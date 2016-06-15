package com.sys.util.encrypt;

public class Idea_bak {

//	void idea_enc(int data11[], /* 待加密的64位数据首地址 */int key1[]) {
//		int i;
//		int tmp, x;
//		int zz[] = new int[6];
//		for (i = 0; i < 48; i += 6) { /* 进行8轮循环 */
//			for (int j = 0, box = i; j < 6; j++, box++) {
//				zz[j] = key1[box];
//			}
//			//x = handle_data(data11, zz);
//			tmp = data11[1]; /* 交换中间两个 */
//			data11[1] = data11[2];
//			data11[2] = tmp;
//		}
//		tmp = data11[1]; /* 最后一轮不交换 */
//		data11[1] = data11[2];
//		data11[2] = tmp;
//		data11[0] = MUL(data11[0], key1[48]);
//		data11[1] = (char) ((data11[1] + key1[49]) % 0x10000);
//		data11[2] = (char) ((data11[2] + key1[50]) % 0x10000);
//		data11[3] = MUL(data11[3], key1[51]);
//	}
//
//	void key_decryExp(int outkey[])/* 解密密钥的变逆处理 */
//	{
//		int tmpkey[] = new int[52];
//		int i;
//		for (i = 0; i < 52; i++) {
//			tmpkey[i] = outkey[wz_spkey[i]];/* 换位 */
//		}
//		for (i = 0; i < 52; i++) {
//			outkey[i] = tmpkey[i];
//		}
//		for (i = 0; i < 18; i++) {
//			outkey[wz_spaddrever[i]] = (char) (65536 - outkey[wz_spaddrever[i]]);/* 替换成加法逆 */
//		}
//		for (i = 0; i < 18; i++) {
//			outkey[wz_spmulrevr[i]] = (char) (mulInv(outkey[wz_spmulrevr[i]]));/* 替换成乘法逆 */
//		}
//	}
}
