package com.yearup.dealership.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory = new ArrayList<>();

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public ArrayList<Vehicle> getVehiclesByPrice(double min, double max){
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getPrice() >= min && vehicle.getPrice() <= max) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model){
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    public ArrayList<Vehicle> getVehiclesByYear(int min, int max){
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getYear() >= min && vehicle.getYear() <= max) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    public ArrayList<Vehicle> getVehiclesByColor(String color){
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getColor().equalsIgnoreCase(color)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    public ArrayList<Vehicle> getVehiclesByMileage(int min, int max){
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getOdometer() >= min && vehicle.getOdometer() <= max) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    public ArrayList<Vehicle> getVehiclesByType(String type){
        ArrayList<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getVehicleType().equalsIgnoreCase(type)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }

    public ArrayList<Vehicle> getAllVehicles(){
        return inventory;
    }

    public void addVehicle(Vehicle vehicle) {
        // Check if a vehicle with the same VIN already exists
        for (Vehicle v : inventory) {
            if (v.getVin() == vehicle.getVin()) {
                System.out.println("Vehicle with this VIN already exists.");
                return;
            }
        }
        inventory.add(vehicle);
        writeVehiclesToCSV();
    }

    public void removeVehicle(Vehicle vehicle) {
        if (!inventory.remove(vehicle)) {
            System.out.println("Vehicle not found in the inventory.");
        } else {
            writeVehiclesToCSV();
            System.out.println("Vehicle removed successfully and inventory updated.");
        }
    }

    // Method to load vehicles from CSV file
    private void loadInventory() {
        File file = new File("inventory.csv");
        if (!file.exists()) return; // No file to load

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                Vehicle vehicle = new Vehicle(
                        Integer.parseInt(data[0]), // VIN
                        Integer.parseInt(data[1]), // Year
                        data[2],                   // Make
                        data[3],                   // Model
                        data[4],                   // VehicleType
                        data[5],                   // Color
                        Integer.parseInt(data[6]), // Odometer
                        Double.parseDouble(data[7]) // Price
                );
                inventory.add(vehicle);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Method to write all vehicles to CSV file
    private void writeVehiclesToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.csv"))) {
            writer.write("vin|year|make|model|vehicleType|color|odometer|price\n");
            for (Vehicle v : inventory) {
                writer.write(toCSV(v) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to inventory file: " + e.getMessage());
        }
    }

    public String toCSV(Vehicle vehicle) {
        return vehicle.getVin() + "|" + vehicle.getYear() + "|" + vehicle.getMake() + "|" + vehicle.getModel() + "|" +
                vehicle.getVehicleType() + "|" + vehicle.getColor() + "|" + vehicle.getOdometer() + "|" + vehicle.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Vehicle> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Vehicle> vehicles) {
        this.inventory = vehicles;
    }
}
