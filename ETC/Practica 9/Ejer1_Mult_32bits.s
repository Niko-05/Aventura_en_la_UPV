          .globl __start
          .data 0x10000000
M:        .space 4
          .text 0x00400000
__start:  
		  li $a0, 'Q'
		  li $a1, Q
		  jal InputV


		  la $a0, M
          la $a1, Q
		  la $a2, R 
          jal MultV

          # Terminar el proceso
          li $a0, 'R'
		  la $a1, R 

PromptV:  li $v0, 11
		  syscall
		  
		  li $v0, 11
		  li $a0, '='
		  syscall
		  
		  li $v0, 1
		  lw $a0, 0($a1)
		  syscall
		  
		  li $v0, 11
		  li $a0, 10
		  syscall
		  jal $ra
		  

MultV:	  lw $t0, 0($a0)
		  lw $t1, 0($a1)
		  
		  bgez $t1, seguir #Q >= 0
		  sub $t0, $zero, $t0
		  sub $t1, $zero, $t1
		  
seguir:	  li $t2, 0
		  beqz $t1, MultVRet
		  
MultVFor: add $t2, $t2, $t0
		  addi $t1, $t1, -1
		  bne $t1, $zero, MultVFor

MultVRet: sw $t2, 0($a2)
		  jr $ra