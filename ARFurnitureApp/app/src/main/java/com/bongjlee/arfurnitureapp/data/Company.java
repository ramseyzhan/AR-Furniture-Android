package com.bongjlee.arfurnitureapp.data;

import java.util.Map;

public final class Company {
    private Map<String, String> companyDetails;
    private String companyName;

    public Company(Map<String, String> companyDetails, String companyName) {
        this.companyDetails = companyDetails;
        this.companyName = companyName;
    }

    public Map<String, String> getCompanyDetails() {
        return companyDetails;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyDetails(Map<String, String> companyDetails) {
        this.companyDetails = companyDetails;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
