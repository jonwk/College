
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