; Practical 4 Sample Solution
; (c) Mike Brady, 2020.

	area	tcd,code,readonly
	export	__main
__main

; Definitions  -- references to 'UM' are to the User Manual.

; Timer Stuff -- UM, Table 173

T0	equ	0xE0004000		; Timer 0 Base Address
T1	equ	0xE0008000

IR	equ	0			; Add this to a timer's base address to get actual register address
TCR	equ	4
MCR	equ	0x14
MR0	equ	0x18

TimerCommandReset	equ	2
TimerCommandRun	equ	1
TimerModeResetAndInterrupt	equ	3
TimerResetTimer0Interrupt	equ	1
TimerResetAllInterrupts	equ	0xFF

; VIC Stuff -- UM, Table 41
VIC	equ	0xFFFFF000		; VIC Base Address
IntEnable	equ	0x10
VectAddr	equ	0x30
VectAddr0	equ	0x100
VectCtrl0	equ	0x200

Timer0ChannelNumber	equ	4	; UM, Table 63
Timer0Mask	equ	1<<Timer0ChannelNumber	; UM, Table 63
IRQslot_en	equ	5		; UM, Table 58

; initialisation code

; Initialise the VIC
	ldr	r0,=VIC			; looking at you, VIC!

	ldr	r1,=irqhan
	str	r1,[r0,#VectAddr0] 	; associate our interrupt handler with Vectored Interrupt 0

	mov	r1,#Timer0ChannelNumber+(1<<IRQslot_en)
	str	r1,[r0,#VectCtrl0] 	; make Timer 0 interrupts the source of Vectored Interrupt 0

	mov	r1,#Timer0Mask
	str	r1,[r0,#IntEnable]	; enable Timer 0 interrupts to be recognised by the VIC

	mov	r1,#0
	str	r1,[r0,#VectAddr]   	; remove any pending interrupt (may not be needed)

; Initialise Timer 0
	ldr	r0,=T0			; looking at you, Timer 0!

	mov	r1,#TimerCommandReset
	str	r1,[r0,#TCR]

	mov	r1,#TimerResetAllInterrupts
	str	r1,[r0,#IR]

	;ldr	r1,=(14745600/1600)-1	 ; 626 us = 1/1600 second
	ldr	r1,=(14745600/1)-1	 ; 1 second
	
	str	r1,[r0,#MR0]

	mov	r1,#TimerModeResetAndInterrupt
	str	r1,[r0,#MCR]

	mov	r1,#TimerCommandRun
	str	r1,[r0,#TCR]

;from here, initialisation is finished, so it should be the main body of the main program
	
IO1DIR	EQU	0xE0028018;
IO1SET	EQU	0xE0028014;
IO1CLR	EQU	0xE002801C;
;IO1PIN	EQU	0xE0028010; as we have no inputs we are not considering PIN register


		ldr	r1,=IO1DIR					;
		ldr	r0,=0x00f00f00				;
		str	r0,[r1]						; making the pins 31-24 and 19-12 and 7-0 outputs 
		ldr	r1,=IO1SET					;
		str	r0,[r1]						; set them to turn the LEDs off	
		ldr	r2,=IO1CLR					;

LoopForever
		
		ldr     r6,=InteruptBoolean		; loading the address of the interupt boolean
        ldr     r7,[r6]					; 
        cmp		r7,#1					; checking till the vaule of interupt boolean is 1
		bne 	LoopForever				;
		
		ldr     r4,=secs0				; loading address of secs[0]
		ldr     r5,=secs1				; loading address of secs[1]
		ldr		r9,[r5]					; loading the value in secs[1]
		cmp		r9,#9					; if( secs[1] == 9)
		bne		secsUnder10				;	secs[0] += 1
		ldr		r9,[r4]					;	
		add		r9,r9,#1				; 
		cmp		r9,#6					; if( secs[0] == 6)
		beq		increaseMins			;	increase minutes, as max valid second is 59, we branch to increase minutes when it reaches 60
		str		r9,[r4]					; storing increased secs[0]
		mov		r9,#0					; and resetting secs[1] to zero as we have completed 10 increments and don't want to
		str		r9,[r5]					; have a hex value, as we're looking for the BCD format
		b		updateTime				; branch to update Time
		
secsUnder10								; if( secs[1] < 9)
		add		r9,r9,#1				; 	secs[1] += 1
		str		r9,[r5]     			; storing increased secs[1]
		b		updateTime				; branch to update Time	
		
increaseMins							;
		ldr		r4,=secs0				; resetting seconds to 00 as we have reached a minute and increasing minutes instead
		mov		r9,#0					; resetting sec[0]
		str		r9,[r4]					;
		
		ldr		r4,=secs1				;
		mov		r9,#0					; resetting sec[1]
		str		r9,[r4]					;
								
		ldr     r4,=mins0				; loading address of mins[0]
		ldr     r5,=mins1				; loading address of mins[1]
		ldr		r9,[r5]					; if( mins[1] == 9)
		cmp		r9,#9					;	mins[0] += 1
		bne		minsUnder10				;
		ldr		r9,[r4]					;
		add		r9,r9,#1				;
		cmp		r9,#6					; if( mins[0] == 6)
		beq		increaseHrs				;	increase hours, as max valid minute is 59, we branch to increase hours when it reaches 60
		str		r9,[r4]					; storing increased mins[0]
		mov		r9,#0					; and resetting mins[1] to zero as we have completed 10 increments and don't want to
		str		r9,[r5]					; have a hex value, as we're looking for the BCD format
		b		updateTime				; branch to update Time
		
minsUnder10								; if( mins[1] < 9)
		add		r9,r9,#1				;	mins[1] += 1
		str		r9,[r5]					; storing increased mins[1] 
		b		updateTime				; branch to update Time

increaseHrs								; resetting seconds to 00 and minutes to 00 as we have reached an hour and increase hours instead
		ldr		r4,=secs0				;
		mov		r9,#0					; resetting sec[0]
		str		r9,[r4]					;
		ldr		r4,=secs1				;
		mov		r9,#0					; resetting sec[1]
		str		r9,[r4]					;
		ldr		r4,=mins0				;
		mov		r9,#0					; resetting min[0]
		str		r9,[r4]					;
		ldr		r4,=mins1				;
		mov		r9,#0					; resetting min[1]
		str		r9,[r4]					;
		
		ldr     r4,=hrs0				; loading address of hrs[0]
		ldr     r5,=hrs1				; loading address of hrs[1]
		
		ldr		r9,[r5]					; if( hrs[1] == 9)
		cmp		r9,#9					;	hrs[0] += 1
		bne		hrsUnder10				;
		ldr		r9,[r4]					;
		add		r9,r9,#1				; if( hrs[0] == 10)
		; comment the lines below if you want to reset the clock after 23:59:59 
		
		; cmp		r9,#10					;	reset time as we would end up with 99 hours
		; beq		ResetTime
		
		; comment the lines above if you want to reset the clock after 23:59:59
		str		r9,[r4]					; storing increased hrs[0]
		mov		r9,#0					; and resetting hrs[1] to zero as we have completed 10 increments and don't want to
		str		r9,[r5]					; have a hex value, as we're looking for the BCD format
		b		updateTime				; branch to update Time
		
hrsUnder10								; if( hrs[1] < 9)
		add		r9,r9,#1				;	hrs[1] += 1
		str		r9,[r5]					; storing increased Time
		
		
		; comment the lines below if you want to reset the clock after 99:59:59
		
		ldr		r10,[r5]				; loading hrs[1]
		ldr		r9,[r4]					; loading hrs[0]
		orr		r10,r10,r9,lsl#4		; using a mask and leftshifting to obtain 0x000000(hrs[0])(hrs[1])
		cmp		r10,#0x00000024			; if(hours == 24)
		beq		ResetTime				;	branch to reset time
										; else
		b		updateTime				; 	branch to update Time
		
		; comment the lines above if you want to reset the clock after 99:59:59
		
ResetTime								;
		ldr		r4,=secs0				;
		mov		r9,#0					; resetting secs[0] to 0
		str		r9,[r4]					;
		
		ldr		r4,=secs1				;
		mov		r9,#0					; resetting secs[1] to 0
		str		r9,[r4]					;
		
		ldr		r4,=mins0				;
		mov		r9,#0					; resetting mins[0] to 0
		str		r9,[r4]					;
		
		ldr		r4,=mins1				;
		mov		r9,#0					; resetting mins[1] to 0
		str		r9,[r4]					;
		
		ldr		r4,=hrs0				;
		mov		r9,#0					; resetting hrs[0] to 0
		str		r9,[r4]					;
		
		ldr		r4,=hrs1				;
		mov		r9,#0					; resetting hrs[1] to 0
		str		r9,[r4]					;
		
		b updateTime					; branch to update Time 

updateTime								;
		ldr	r1,=IO1DIR					; Re-intialising the pins
		ldr	r0,=0x00f00f00				; Setting the pins 31-24 and 19-12 and 7-0
		str	r0,[r1]						; Storing back to the DIR register
		
		ldr	r5,=secs1					; Loading secs[1]
		ldr	r6,[r5]						; format - hrs[0].hrs[1] : mins[0].mins[1] : secs[0].secs[1]
		mov r7,r6, LSL#0				; storing time in r7
		
		ldr	r5,=secs0					; loading secs[0]
		ldr r6,[r5]						; using orr inorder to not change previous values while writing new values
		orr r7,r6, LSL#4				; left shifting by 4 in order to fit secs[0] in our required format
		
		ldr	r5,=mins1					; loading mins[1]
		ldr r6,[r5]						;
		orr r7,r6, LSL#12				; left shifting by 12 in order to fit mins[1] in our required format
		
		ldr	r5,=mins0					; loading mins[0]
		ldr r6,[r5]						;
		orr r7,r6, LSL#16				; left shifting by 16 in order to fit mins[0] in our required format
		
		ldr	r5,=hrs1					; loading hrs[1]
		ldr r6,[r5]						;
		orr r7,r6, LSL#24				; left shifting by 24 in order to fit hrs[1] in our required format
		
		ldr	r5,=hrs0					; loading hrs[0]
		ldr r6,[r5]						;
		orr r7,r6, LSL#28				; left shifting by 28 in order to fit hrs[0] in our required format
		
		orr	r7,r7,r0					; using #0x00f00f00 as a mask to get f inplace of : as our required format
		
		str	r7,[r1]						; writing our time in r7 to the DIR register to display it on the GPIO0
		
		ldr     r6,=InteruptBoolean		; resetting Interupt Boolean
		mov 	r7,#0					; setting it to zero
		str		r7,[r6]					; so that it can be set to 1 when ever an interrupt occurs
		b LoopForever					; braching back to LoopForever to loop infinitely

	AREA	InterruptStuff, CODE, READONLY
irqhan	sub	lr,lr,#4
	stmfd	sp!,{r0-r1,lr}	; the lr will be restored to the pc

; the main purpose of the interrupt handler is merely to update a tick counter
        ldr     r0,=InteruptBoolean		; loading the Interrupt Boolean from memory
		mov 	r1,#1					; 
        str     r1,[r0]					; setting the Interrupt Boolean to 1

;this is the body of the interrupt handler

;here you'd put the unique part of your interrupt handler
;all the other stuff is "housekeeping" to save registers and acknowledge interrupts


;this is where we stop the timer from making the interrupt request to the VIC
;i.e. we 'acknowledge' the interrupt
	ldr	r0,=T0
	mov	r1,#TimerResetTimer0Interrupt
	str	r1,[r0,#IR]	   	; remove MR0 interrupt request from timer

;here we stop the VIC from making the interrupt request to the CPU:
	ldr	r0,=VIC
	mov	r1,#0
	str	r1,[r0,#VectAddr]	; reset VIC

	ldmfd	sp!,{r0-r1,pc}^	; return from interrupt, restoring pc from lr
				; and also restoring the CPSR

; TCD Read-Write Definitions.

		AREA	MutableDate, DATA, READWRITE
InteruptBoolean      space 4
hrs0       			space 4
hrs1       			space 4
mins0       		space 4
mins1				space 4	
secs0       		space 4
secs1       		space 4
                END
