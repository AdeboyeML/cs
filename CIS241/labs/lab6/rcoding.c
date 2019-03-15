#include "coding.h"
#include "limits.h"
/* encode characters into unsigned long integers and save the encoded integers
 * in a file.
 * charaters are read from a file.
 * infile: input file, contains plaintext.
 * outfile: output file, where encoded integers are saved.
 * return 0 on success, and 1 on failure.
 */
int encode(FILE * infile, FILE * outfile)
{
	// return if either of the file pointers is NULL
	if(infile == NULL || outfile == NULL)
		return 1;

	// Need to determine the size of unsigned long
	int ul_size = sizeof(unsigned long int);
	// Need to determine the size of char
	int ch_size = sizeof(unsigned char);

	// number holder and char holder
	unsigned long int ul_holder = 0;
	unsigned char ch_holder;

	int n = 0, ch_per_ul = ul_size / ch_size;
	int ch_size_bits = ch_size * 8;

	/* do the coding */
	while(fscanf(infile, "%c", &ch_holder) != EOF)
	{
		ul_holder = ul_holder | ch_holder;
		
		if (n < (ch_per_ul - 1))
			ul_holder = ul_holder << ch_size_bits;

		if ((++n) == ch_per_ul)
		{
			//done this int, save it & initialize for next one
			fprintf(outfile, "%lu, ", ul_holder);
			n = 0;
			ul_holder = 0;
		}
	}
	
	/* pad the last unsigned long integer with '\0' */
	if(ul_holder != 0)
	{
		ch_holder = '\0';
		while(1)
		{
			ul_holder = ul_holder | ch_holder;
			if((++n) >= ch_per_ul)
			{
				fprintf(outfile, "%lu, ", ul_holder);
				n = 0;
				ul_holder = 0;
				break;
			}
			else
			{
				ul_holder = ul_holder << ch_size_bits;
			}
		}
	}
	
	return 0;
}

/* decode unsigned long integers into chars and save them to another file.
 * infile: input file, contains unsigned long integers.
 * outfile: output file, used to save decoded characters (plain text).
 * return 0 on success and 1 on failure.
 */
int decode(FILE * infile, FILE * outfile)
{
	int i;
	unsigned char ch, mask = 0;
	unsigned long int ul;
	char ch_size_bytes = sizeof(unsigned char);
	char ch_size_bits = ch_size_bytes * CHAR_BIT;
	char ul_size_bytes = sizeof(unsigned long int);
	char ch_per_ul = ul_size_bytes / ch_size_bytes;

	while(fscanf(infile, "%lu, ", &ul) != EOF){
		for(i = ch_per_ul - 1; i >= 0; i--){
			ch = (ul >> i * ch_size_bits) ^ mask;
			if(ch != '\0')
				fprintf(outfile, "%c", ch);
		}
	}
	return 0;
}
