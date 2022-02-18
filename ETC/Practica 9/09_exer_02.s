          .globl __start
          .data 0x10000000
demana:   .asciiz "Escriviu-me alguna cosa: "
cadena:   .space 80             
digueu:	  .asciiz "Heu escrit: "

          .text 0x00400000
__start:  la $a0, demana        
          la $a1, cadena        
          li $a2, 80            
          jal InputS
		  la $a0, digueu
		  la $a1, cadena
		  jal PromptS
          li $v0,10
          syscall

InputS:   li $v0, 4
          syscall
          li $v0, 8
          move $a0, $a1
          move $a1, $a2
          syscall
          jr $ra
		  
PromptS:  li $v0, 4
		  syscall
		  
		  move $a0, $a1
		  li $v0, 4
		  syscall
		  jr $ra
