package com.domain.helpers;

import com.domain.services.TransactionService;

public class CommonUtil {

    public static String getSuccesMessage(String type, String name) {
        String result = "Berhasil melakukan pengisian " + type + ", [" + name + "]";
        return result;
    }

    public static String getSuccesTransaction(String type, String name, String liter) {
        String result = name + " mengisi pertamax jenis " + type + " sisa maksimal pengisian: " + liter;
        return result;
    }

    public static String getSuccessTransactionHistory(String name, String liter) {
        String result = name + " telah mengisi: " + liter + " liter";
        return result;
    }

    public static String getSuccessUpdateUser(String name, String liter) {
        String result = name + " berhasil melakukan pengisian ulang sebanyak " + liter + " liter ";
        return result;
    }

    public static String getSuccessBalance(String type, String liter) {
        String result = type + " : " + liter + " liter";
        return result;
    }

    public static String getErrOtherMessage() {
        String result = "Terjadi kesalahan saat memproses transaksi";
        return result;
    }

    public static String getErrMessageInsufience() {
        String result = "Maaf, sisa pertamax tidak mencukupi";
        return result;
    }

    public static String getErrMessageInquiryAccount() {
        String result = "Nama dan kendaraan belum terdaftar";
        return result;
    }

    public static String getErrMessageInquiryLiter(String name, String type, String liter) {
        String result = name + " jenis " + type + " sisa maksimal pengisian: " + liter;
        return result;
    }

    public static String updateLiter(TransactionService transactionService, String literTrans, String literUser,
            String literTransaction, String userId) {
        Integer literAvailable = Integer.parseInt(literUser);
        Integer literCurrent = Integer.parseInt(literTransaction);
        Integer updatedLiter = literAvailable - literCurrent;

        transactionService.updateTransaction(updatedLiter.toString(), userId);

        return updatedLiter.toString();
    }
}
