# G3Computer
Simulator of a CSIC machine

How to Use:
You can click each radio button to set the value of those registers and click the Set buttons to confirm making changes. And before clicking the Run or Single Step button, you need to click the IPL button first to read initial program. The Run button would run all the instructions stored in the Memory sequentially, and Single Step is used to execute the current instruction.

The Initial Program:
The initial program is indeed the boot program, which is loaded through the IPL button into the memory, starting from address 0. It has 6 instructions:

// Load R[3] from Memory[7] whose data is 256

0000011100000111

// Store R[3] whose data is 256 to Memory[8]

0000101100001000

// AMR Add R[2] and Memory[8] to R[2]

0001001000001000	

// SMR Sub R[2] and Memory[9] to R[2]

0001011000001001

// AIR R[1] + 4       

0001100100000100	

// SIR R[1] - 2

0001110100000010

And 3 data in address 7, 8, 9 respectively:
Memory[7] = 256,
Memory[8] = 0,
Memory[9] = 140,

Once encountered an unexpected instruction, our computer would reboot automatically with PC pointing to the first address of the boot program.
