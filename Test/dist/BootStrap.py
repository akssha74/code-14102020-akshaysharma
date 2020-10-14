import sys
sys.path.append(r"C:\Users\Akshay Sharma\Desktop\Test\dist\bmi-1.0-py3.7.egg")
from bmi.BMIPython import BMICalculator

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

obj = BMICalculator(data,bmi_table)
print(obj.add_columns_and_get_count())