import sys

opcode_dict = {
            'HLT': 0,
            'TRAP': 036,

            'LDR': 01,
            'STR': 02,
            'LDA': 03,
            'LDX': 041,
            'STX': 042,
            'LIX': 043,
            'IN': 061,
            'OUT': 062,
            'CHK': 063,
            'GETS': 064,
            'PUTS': 065,

            'AMR': 04,
            'SMR': 05,
            'AIR': 06,
            'SIR': 07,
            'MLT': 020,
            'DVD': 021,
            'TRR': 022,
            'AND': 023,
            'ORR': 024,
            'NOT': 025,
            'CMT': 026,
            'CMB': 027,
            'SRC': 031,
            'RRC': 032,
            'LE': 033,
            'GE': 034,
            'ET': 035,

            'JZ': 010,
            'JNE': 011,
            'JCC': 012,
            'JMA': 013,
            'JSR': 014,
            'RFS': 015,
            'SOB': 016,
            'JGE': 017,
            }

with open('./src/computer/Program 2') as in_file:
    with open('Program 2.txt', 'w') as sys.stdout:
        while True:
            line = in_file.readline()
            if not line:
                break
            
            content = line.strip()
            
            if not content or content.startswith('//'):
                print line,
                
            elif content.startswith('/*'):
                print line,
                
                while True:
                    line = in_file.readline()
                    if not line:
                        break
                    
                    content = line.strip()
                    
                    print line,
                    
                    if content.endswith('*/'):
                        break
                    
            else:
                head, rest = content.split(' ', 1)
                opcode = opcode_dict[head]
                rest = [int(s.strip()) if s.strip() != '_' else 0 for s in rest.split(',')]
                instruction = opcode << 10 | rest[0] << 8 | rest[1] << 6 | rest[2] << 5 | rest[3]
                print line, line[: line.find(content)] + '{0:016b}'.format(instruction)
