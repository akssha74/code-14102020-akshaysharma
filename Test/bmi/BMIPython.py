# -*- coding: utf-8 -*-
"""
Created on Wed Oct 14 18:36:52 2020

@author: Akshay Sharma
"""


class BMICalculator:
    

    def __init__(self,data,bmi_table):
        '''
        Initialize the class with Patient data and BMI table
        '''        
        self.data = data;
        self.bmi_table=bmi_table;
    
    

    def calculate_BMI(self,row):
        '''
        calculate BMI and round to 2 places
        '''
        return round(row["WeightKg"]/((row["HeightCm"]/100)*(row["HeightCm"]/100)),2)
    
    

    def get_health_risk(self,bmi_value):
        '''
        check the health risk corresponding to calculated BMI
        '''
        if(bmi_value <= 18.4):
            return "Malnutrition risk"
        elif(18.5 <= bmi_value <= 24.9):
            return "Low risk"  
        elif(25 <= bmi_value <= 29.9):
            return "Enhanced risk" 
        elif(30 <= bmi_value <= 34.9):
            return "Medium risk" 
        elif(35 <= bmi_value <= 39.9):
            return "High risk" 
        else:
            return "Very high risk" 
        
        
    def get_bmi_category(self,bmi_value):
        '''
        check the BMI category corresponding to calculated BMI
        '''
        if(bmi_value <= 18.4):
            return "Underweight",0
        elif(18.5 <= bmi_value <= 24.9):
            return "Normal weight",0  
        elif(25 <= bmi_value <= 29.9):
            return "Overweight",1
        elif(30 <= bmi_value <= 34.9):
            return "Moderately obese",0 
        elif(35 <= bmi_value <= 39.9):
            return "Severely obese",0
        else:
            return "Very severely obese",0 
    
    def add_columns_and_get_count(self):
        '''
        Add columns using helper functions
        '''
        over_count = 0
        data = self.data
        for row in data:
            bmi_value = self.calculate_BMI(row)
            row["BMI"] = "{:.2f}".format(bmi_value)
            bmi_category,count = self.get_bmi_category(bmi_value)
            row["BMI Category"] = bmi_category
            row["Health risk"] = self.get_health_risk(bmi_value)
            over_count = over_count+count
        return data,over_count
    
    


if __name__ == "__main__":
    
    data = [{"Gender": "Male", "HeightCm": 171, "WeightKg": 96 },
    { "Gender": "Male", "HeightCm": 161, "WeightKg": 85 },
    { "Gender": "Male", "HeightCm": 180, "WeightKg": 77 },
    { "Gender": "Female", "HeightCm": 166, "WeightKg": 62},
    {"Gender": "Female", "HeightCm": 150, "WeightKg": 70},
    {"Gender": "Female", "HeightCm": 167, "WeightKg": 82}]
    
    
    bmi_table = [("BMI Category","BMI Range (kg/m2)","Health risk"),
    ("Underweight","18.4 and below","Malnutrition risk"),
    ("Normal weight","18.5-24.9","Low risk"),
    ("Overweight","25-29.9","Enhanced risk"),
    ("Moderately obese","30-34.9","Medium risk"),
    ("Severely obese","35-39.9","High risk"),
    ("Very severely obese","40 and above","Very high risk")]    
    
    obj  = BMICalculator(data, bmi_table);
    print(obj.add_columns_and_get_count())
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    