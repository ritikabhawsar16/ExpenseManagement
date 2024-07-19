package com.adt.expensemanagement.util;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceNumberGenerator {
        private static final AtomicLong ibInvoiceCounter = new AtomicLong(0);
        private static final AtomicLong obInvoiceCounter = new AtomicLong(0);
        public static String generateInvoiceNumber(boolean projectType) {
            String prefix = projectType ? "INV-OB" : "INV-IB";
            long counter = projectType ? ibInvoiceCounter.incrementAndGet() : obInvoiceCounter.incrementAndGet();
            String invoiceNumber = String.format("%s%03d", prefix, counter);
            return invoiceNumber;
        }
}



