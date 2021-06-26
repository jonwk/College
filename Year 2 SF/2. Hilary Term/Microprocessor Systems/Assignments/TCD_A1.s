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
		
;----------------------------------------------ROUGH-WORK----------------------------------------------
; Please ignore stuff below this
;
;
;factorial
;  		 CMP 	R4, #0      ; if argument n is 0, return 1
;        MOVEQ 	R4, #1
;        MOVEQ 	PC, LR
;        MOV 	R3, R4      ; otherwise save argument n into R3
;		 SUB 	R4, R4, #1  ; and perform recursive call on R3 - 1
;        ADD 	SP, SP, #-8 ; decrement the stack pointer by 8 byte, and thus      
							; allocating space to save 2 words. 
;		 STR 	LR, [SP,#4] ; push/save the current link Register into the 1st word. 
;		 STR 	R3, [SP]     ; push/save the current value of R3 into the 2nd word. 
;		 BL 		factorial
;		 LDR 	R3, [SP]   	; restore the  value of R3 that I saved on the stack in                        
							; the most recent iteration. 
        
		;SMULL 	R4,R5,R3,R4; R0-R4, R
;		 UMULL	R4,R7,R3,R4
;		 UMULL	R5,R8,R3,R5
;		 ADD		R5,R7,R5
		
		;MUL 	R4, R3, R4  ; multiply returned value by n
;		 LDR 	LR, [SP,#4] ; restore the value of Link Register that I saved on the  
							; stack in the most recent iteration.  
;		 ADD 	SP,SP,#8    ; After restoring the desired values, deallocate their  
							; memory space on the stack by incrementing the stack   
							; pointer by the same 8 bytes. 
;		 MOV 	PC, LR      ; and return

		
; fact subroutine
;  calculates 64 bit factorial for n, a natural number
; Parameters:
;  R0 - n value 
; Return:
;  R0 - most significant 32 bits of the factorial
;  R1 - least significant 32 bits of the factorial
;fact
;   STMFD	sp!, {LR,R3-R5}		; storing the registers so we dont damage the contents of them inside the subroutine
;	MOV 	R5,#0x00000000		; We will be using R6 to set the  the C bit of the CPSR
;	CMP		R2,#0				; We use R2 to store the product of n*fact(n-1)
;	MOVEQ	R2,#1				; We initialize R2 to 1 if it is set to zero
;	CMP     R0, #0              ; if n == 0 return 1 which is stored in R2
;   BEQ     return_r0_r1        ; If n == 0 we branch to return_r0_r1
;	UMULL	R2,R3,R0,R2			; R3-R2 = R0 * R2 we multilply two 32 bit values, and store the 64 bit value corresponding to the LSB in R3toR2
;	UMULL	R1,R4,R0,R1			; We do the same multiplication as above but for the most significant bits part, we store carry for LSBs and MSBs;
;	ADD		R1,R3,R1			;   in R3 and R4 Respectively we add the carry of the LSBs to get a valid product and check MSBs carry for overflow
;	CMP		R4,#0				; if the Most significant 32 bits of MSB product is anything other than 0 it means we have overflow
;	BEQ		c_is_zero			; likely an error and set the C bit in CPSR to 1
;	MOV 	R5,#0x20000000		; NVCZ - 0010 - 0x200....
;	MSR     CPSR_f,R5			; Setting C bit in CPSR to 1 if we 
;	MOV 	R1,#0x00000000		; I'm considering my product to be 0xFFFFFFFF as we have encountered a factorial which is 
;	MOV 	R0,#0x00000000		; over 64 bits, these can be commented out to see the factorial without the overflow
;	MOV		R2,#0x00000000
;	B		fact_over
;c_is_zero
;    SUB    	R0, R0,#1           ; n = n-1, and calling the function again making it n*fact(n-1)
;    BL      fact                ;
;return_r0_r1
;	CMP		R2,#0				; if R2 != 0, we prepare R0 and R1 to be returned
;	BEQ		fact_over			;	
;	MOV		R0,R1				; we set the 32 Most Significant Bits of the 64 bit factorial to R0
;	MOV		R1,R2				; and the 32 Least Significant Bits of the 64 bit factorial to R1
;	MOV		R2,#0				; and set R2 to zero, reinitializing it inorder to make the subroutine "Well Behaved"
;fact_over
;	MSR     CPSR_f,R5			; setting the C bit is in the CPSR
;    LDMFD   sp!,{LR,R3-R5}     	; restore the contents of the used registers for the main program
;    BX 		LR 					; return program execution to the caller