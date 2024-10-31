package com.yearup.dealership.pluralsight;

public class SalesContract extends Contract{
    private double salesTax;
    private int recordingFee;
    private int processingFee;
    private boolean toFinance;

    public SalesContract(String dateOfContract, String customerName, String customerEmail, int vehicleSold, boolean toFinance) {
        super(dateOfContract, customerName, customerEmail, vehicleSold);
        this.toFinance = toFinance;
    }

    public boolean isToFinance() {
        return toFinance;
    }

    public void setToFinance(boolean toFinance) {
        this.toFinance = toFinance;
    }

    public double getSalesTax() {
        return getTotalPrice() * 0.05;
    }

    public int getRecordingFee() {
        return 100;
    }

    public int getProcessingFee() {
        if (getVehicleSold() < 10_000) {
            return 295;
        } else {
            return 495;
        }
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold() + getSalesTax() + + getRecordingFee() + getProcessingFee();
    }

    @Override
    public double getMonthlyPayment() {
        if (isToFinance()) {
            if (getVehicleSold() >= 10_000) {
                return loanCalculator(4.25 / 12 / 100, 48);
            } else {
                return loanCalculator(5.25 / 12 / 100, 24);
            }
        } else {
            return 0;
        }
    }

    public double loanCalculator(double monthlyInterestRate, int months) {
        double p = getVehicleSold();
        return p * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months)) /
                (Math.pow(1 + monthlyInterestRate, months) - 1);
    }
}
