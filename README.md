# G3Computer
Simulator of a CSIC machine

How to Use:
You can click each radio button to set the value of those registers and click the Set buttons to confirm making changes. And before clicking the Run or Single Step button, you need to click the IPL button first to read initial program. The Run button would run all the instructions stored in the Memory sequentially, and Single Step is used to execute the current instruction.

New instructions, or programs, can be loaded into memory in one of the three format: Hexadecimal, octal and binary.

The Initial Program:
The initial program (or boot program) is set as accepting 20 unsigned integers (0 - 65535), and one more to find the closest match among the 20 ones.

Once encountered an error (unexpected instruction or accessing wrong memory address), our computer would reboot automatically with PC pointing to the first address of the boot program.
