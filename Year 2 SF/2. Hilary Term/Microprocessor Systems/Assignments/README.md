# Assignment 1 -- Programming and Project Development
This assignment is worth 8% of your module result.
  
The deadline for submission is Thursday March 11, midnight.
  
Write a complete Keil MDK project comprising a main program and a subroutine.
  
The subroutine must be called “fact” and should calculate the factorial of the number passed to it in R0. The subroutine must be recursive. Zero marks will be awarded for a non-recursive solution.
  
The purpose of the main program is to demonstrate and test the subroutine.
  
Calculations  must be done in 64-bit unsigned arithmetic and the result must be returned in R0 & R1, with the most significant 32 bits in R0 and the least significant 32 bits in R1.
  
If any error occurs, the C bit of the CPSR must be set and a result of 0 returned in R0 & R1. If there are no errors, the C bit must be clear.
  
The main program should use the subroutine four times:
  Calculate the factorial of 5.
  Calculate the factorial of 14.
  Calculate the factorial of 20.
  Calculate the factorial of 30.

Each result should be stored, by the main program, as a 64-bit result in RAM, starting at 0x40000000. Do this by reserving four 8-byte spaces at the start of the read-write area.

Note that the ARM7 does not do 64-bit arithmetic — you’ll have to somehow make use of 32-bit calculations to do 64-bit arithmetic.
  
Do not use repeated addition to implement multiplication. Zero marks will be awarded for a solution that uses repeated addition.
  
Please create a plain text file called “qanda.txt” ( for Questions AND Answers) and include it in your project folder. In it, please answer the following questions very briefly (one or two sentences each, max):
  1. What does “well behaved” mean in the context of subroutines?
  2. Explain how/why your subroutine is “well behaved”.
  3. How would you test that your subroutine is well behaved?
  4. Why is using repeated addition to implement multiplication such a bad idea?
  5. What would happen to the program if a very large number of recursive calls were made, i.e. if there was very "deep" recursion?
    
Marks will be awarded as follows:
  1. Has a Keil project, including the quanda.txt file, been submitted? (2)
  2. Does the project assemble with no errors or warnings? (2)
  3. Is the progam well organised and structured? (3)
  4. Are the correct answers produced and stored correctly? (8)
  5. Are the follow-on questions answered correctly? (5)
    
Please submit the entire project folder as a zip archive (a "Compressed (zipped) folder" in Windows) to Blackboard by the deadline.

```arm

; Sample program makes the 4 LEDs P1.16, P1.17, P1.18, P1.19 go on and off in sequence
; (c) Mike Brady, 2020.

	area	tcd,code,readonly
	export	__main
__main

;Zero factorial test - works
	MOV	R1, #0;
	MOV R0, #0;
	BL fact

;Rest of the test
	LDR R5, =inputs; input address
	LDR R8, =fact_results; results address
	MOV R9,#0; second index

for1
	CMP	R7,#16; 4 elements * 4 bits
	BEQ endfor1
	
	MOV	R1, #0;
	MOV R0, #0;
	LDR R0,[R5,R7];	R7 - offset, R5 - input address
	
	BL fact
	
	STR R0,[R8,R9]; R8- results address R9 - offset R0 - MSB
	ADD R9,R9,#4; next space
	STR R1,[R8,R9]; R8- results address R9 - offset R1 - LSB
	ADD	R9,R9,#4; next space
	
	ADD R7,R7, #4; next input element offset
	B for1
endfor1


; To see if we can load the right values into registers from memory
	MOV R7, #0;
for2
	CMP	R7,#32;
	BEQ endfor2
	
	LDR R0,[R8,R7];
	ADD R7,R7,#4;
	LDR R1,[R8,R7];	
	ADD R7,R7, #4;
	
	B for2
endfor2


fin	b	fin

; fact subroutine
;  calculates 64 bit factorial for n, a natural number
; Parameters:
;  R0 - n value 
; Return:
;  R0 - most significant 32 bits of the factorial
;  R1 - least significant 32 bits of the factorial

fact
         STMFD	sp!, {LR,R2-R5} ; storing the registers so we dont damage the contents of them inside the subroutine
		 MOV R5,#0x00000000     ; We will be using R6 to set the  the C bit of the CPSR
		 MSR CPSR_f,R5          ; setting the C bit is in the CPSR
         MOV R3,R0              ;
         CMP R3,#1              ; recursion until n > 1
         BGT fact_n_1           ; 
         MOV R1,#1              ; return 1 when n<=1 
         MOV R0,#0              ;
         B fact_return          ;
fact_n_1
         SUB R0,R0,#1 ;
         BL fact              ; call fact(n-1)
         
         MOV R2,#0
         ;MUL R1,R3,R1              ; return n * fact(n-1) 
		 UMULL	R1,R2,R3,R1			; R2-R1 = R3 * R1 we multilply two 32 bit values, and store the 64 bit value corresponding to the LSB in R2toR1
		 UMULL	R0,R4,R3,R0			; We do the same multiplication as above but for the most significant bits part, we store carry for LSBs and MSBs
		 ADD	R0,R2,R0            ; Adding the LSB carry to the MSB
		 CMP	R4,#0			    ; if the Most significant 32 bits of MSB product is anything other than 0 it means we have overflow
		 BEQ	fact_return		    ; likely an error and set the C bit in CPSR to 1
		 MOV 	R5,#0x20000000		; NVCZ - 0010 - 0x200....
		 MSR    CPSR_f,R5			; Setting C bit in CPSR to 1 if we 
		 MOV 	R1,#0x00000000		; I'm considering my product to be 0 as we have encountered a factorial which is 
		 MOV 	R0,#0x00000000		; over 64 bits, these can be commented out to see the factorial without the overflow
fact_return
        LDMFD   sp!, {PC,R2-R5}     ; restore the contents of the used registers for the main program
        BX 		LR 					; return program execution to the caller	
	
	
	area	tcdrodata,data,readonly
inputs dcd	5
	dcd	14
	dcd	20
	dcd	30
		
	area	tcddata,data,readwrite
fact_results	space	32

	end
```

# Assignment 2 -- Polling
This assignment is worth 10% of your module result.
    
The deadline for submission is Monday April 5, midnight.
  
Write a complete program for the Keil Development System that can run and be demonstrated in the Emulator.
  
The program should allow you to do simulated input and output on the simulated GPIO Port 1.
  
The program should monitor inputs on Pins 24 to 27 and should provide a response on Pins 23 to 16 as follows:
  - Pins 23 -- 16 should be treated as a 8-bit number "D" whose value is initially 0. Pin 23 is the MSB, Pin 16 the LSB.
  - Pins 27 -- 24 should be treated as individual inputs, where a pin's value going from 1 to 0 is to be interpreted as a corresponding [imaginary] push-button being pressed and the transition from 0 to 1 is is to be interpreted as the release of the button. For example, if Button 23 is pressed and released, Pin 23's value will go from 1 to 0; then, when Button 23 is released, the value of Pin 23 will return from 0 to 1. You can click theses values in the emulator.

The program should do the following:
  - Pressing Button 24 should add 1 to the value of D.
  - Pressing Button 25 should subtract 1 from the value of D.
  - Pressing Button 26 should shift the bits in D to the left by one bit position.
  - Pressing Button 27 should shift the bits in D to the right by one bit position.
    
Marks will be awarded for a well structured program. You should consider the separate components of the program, and write individual subroutines for them.
  
Please create a plain text file called “qanda.txt” ( for Questions AND Answers) and include it in your project folder. In it, please answer the following questions very briefly (one or two sentences each, max):
  1. What does the term "Memory Mapped" in "Memory Mapped I/O" mean?
  2. What is the difference between a byte-sized memory-mapped interface register and a regular byte of RAM?
  3. Why is polling considered to be inefficient?
  4. How could you organise polling of two or more interfaces?
  5. Why would polling be bad for a computer's energy consumption?
    
Marks will be awarded as follows:
  1. Has a Keil project been submitted? (2)
  2. Does the project assemble with no errors or warnings? (2)
  3. Is the progam well organised and structured? (3)
  4. Does the program behave correctly? (8)
  5. Are the follow-on questions answered correctly? (5)
    
Please submit the entire project folder as a zip archive (a "Compressed (zipped) folder" in Windows) to Blackboard by the deadline.

```arm

	area	tcd,code,readonly
	export	__main
__main


IO1DIR	EQU	0xE0028018;
IO1SET	EQU	0xE0028014;
IO1CLR	EQU	0xE002801C;
IO1PIN	EQU	0xE0028010;
	
	LDR	R1,=IO1DIR
	LDR	R2,=0x00ff0000		; select P1.23--P1.16
	STR	R2,[R1]				; make them outputs
	;ldr	R1,=IO1SET
	;str	R2,[R1]			;set them to turn the pins off
	;ldr	R2,=IO1CLR
	
	
	LDR	R4,=IO1PIN			; point to the pin register of GPIO1
	MOV R5,#0x0F000000		; setting our default inputs
	STR R5,[R4]				;
	
	MOV R7,#0				; Result D - 00 to ff initialization 

whRepeat					; while (forever) {
	LDR	R6, [R4]			;   lastState = IO1PIN
	
	CMP R7, #0x000000FF		; as D is a 8 bit value, 0<=D<=FF , optional error handling
	BLS whPoll				; if (D > FF)
	mov R7,#0;				;	D = 0

whPoll						;   do {
	LDR	R5, [R4]			;     currentState = IO1PIN1
	CMP	R5, R6				;
	BEQ	whPoll				;   } while (currentState == lastState)

	AND	R8, R5, #0x0F000000 ; Mask to obtain input pins (27-24)

	CMP	R8, #0x0E000000		; Button 24 should add 1 to the value of D
	BNE	notAdd				; (0001 <-> 1110) checking for bit 24
	ADD	r7, r7, #1			; R7++ (D ++)
	B updateOutput			; update output
notAdd

	CMP	R8, #0x0D000000		; Button 25 should subtract 1 from the value of D
	BNE	notSub				; (0010 <-> 1101) checking for bit 25
	CMP	R7,#0				; if R7 is zero then 2's compliment will be returned 
	BNE	nonNegative			; but this is a 8 bit value so two's complement will start from FF
	MOV	R7, #0x000000FF		; unlike FFFFFFFF usually
	B updateOutput			; update output
nonNegative
	SUB	r7,r7, #1			; R7-- (D --)
	B updateOutput			; update output
notSub
	
	CMP	R8, #0x0B000000		; Button 26 should shift the bits in D to the left by one bit position
	BNE	notLeftShift		; (0100 <-> 1011) checking for bit 26
	MOV	R7, R7, LSL #1		; R7 LSL (D LSL)
	B updateOutput			; update output
notLeftShift

	CMP	R8, #0x07000000		; Button 27 should shift the bits in D to the right by one bit position.
	BNE	notRightShift		; (1000 <-> 0111) checking for bit 27
	MOV	R7, R7, LSR #1		; R7 RSL (D LSR)
	B updateOutput			; update output
notRightShift
	
updateOutput				; Updating output in pins 23-16
	MOV R8, R7 ,LSL#16		; Left shifting by 16 to get our result in required pins
	STR	R8,[R4]				; Storing the output D to pin register of GPIO1

waitAll						; after updating the result we wait till all the pins are turned off
	LDR	R8, [R4]			; to make sure the operations don't interfere with each other
	AND	R8, #0x0F000000		; Checking all the bits 27-24 are set
	CMP	R8, #0x0F000000		; waiting till they are set
	BNE waitAll				;
	
	B	whRepeat			; } branching back to the initial while loop
	

	end
```

# Assignment 3 -- Interrupt Handling
This assignment is worth 10% of your module result.
    
The deadline for submission is Wednesday April 21, midnight.
  
Write a complete program for the Keil Development System that can run and be demonstrated in the Emulator.
  
The main program should "display" the elapsed time, in hours, minutes and seconds, since the program started running, on the 32 bits of GPIO 1 using Binary Code Decimal (BCD) format. As the time goes by, the "display" should update appropriately. It must be exact.

For example, say the time to display is "12:34:56". Each character occupies four bits (a "nybble") and  use "1111" as a spacer instead of of the ":". This means that the above time would appear as:
00010010111100110100111101010110
in the GPIO (that is: "1 2 1111 3 4 1111 5 6" or, in hexadecimal: 0x12F34F56).
  
The time should be absolutely exact -- that is, it should be as accurate as the hardware of the computer will allow.
  
The program should consist of a main program (and subroutines, if necessary) an an interrupt handler. Once initialised, your program should run in the user mode.
  
You interrupt handler should somehow make use of one of the timers in the LPC2138. It should be extremely simple and very fast. It should do the absolute minimum necessary at interrupt level. Everything else should run in the main program. In particular, the interrupt handler must not have anything to do with the displaying of the elapsed time on the GPIO -- that is the responsibility of the main program.
  
The main program will consist basically of two parts:
  1. The "initialisation" part of the main program sets everything up for subsequent operation. So, it must set up any data necessary, configure the timer and the Vectored Interrupt Controller and finally enable interrupts.
  Note: It is really important to ensure everything is absolutely ready before you enable interrupts, because as soon as interrupts are enabled, it is possible that an interrupt will immediately occur. If all your initialisation is incomplete, then it may cause the interrupt handler to misbehave.
    
  2.The "application" part that actually implements the display. As you know, the system starts up in a privileged mode. When the initialisation part of your main program is finished, make the rest of the program run in the User Mode. (Obviously, the interrupt handler will run in Interrupt Mode.)
    
Grading criteria
  1. Project Submitted (2).
  2. Program assembles without errors or warnings (2).
  3. Program works (4).
  4. Main program runs in User Mode (1).
  5. Interrupt handler is simple (2).
  6. Structure of program (2).
  7. Good comments (2).
  8. Review questions (5).
    
Review Questions - Please answer each question very briefly, preferably in one sentence.
  1. What is the difference between "system" mode and "supervisor" mode on the ARM 7?
  2. When the term "preemptive" is used in respect of a thread or process scheduler, what does it mean? 
  3. Based on what we have discussed in class, why it is inadvisable to use a general-purpose operating system in a situation where real-time operation must be guaranteed?
  4. What basic hardware facilities are provided to enable system builders to enforce privilege outside the CPU ("off-chip")? 
  5. Overall, which is more efficient: interrupt-driven I/O or polled I/O? In your answer, explain why and roughly estimate the difference in efficiency.
    
Marks will be awarded for a well structured program. You should consider the separate components of the program, and write individual subroutines for them.
  
Marks will be awarded as follows:
  1. Has a Keil project been submitted? (2)
  2. Does the project assemble with no errors or warnings? (2)
  3. Is the progam well organised and structured? (3)
  4. Does the program behave correctly? (8)
  5. Are the follow-on questions answered correctly? (5)
Please submit the entire project folder as a zip archive (a "Compressed (zipped) folder" in Windows) to Blackboard by the deadline.
    
```arm

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
```
