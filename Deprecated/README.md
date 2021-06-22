# inventory-app
A program for tracking the contents of a users pantry / fridge by best before date

@author Darianisak
@start_date 04 04 2021

This program's purpose is to catalogue the contents of a users pantry/fridge, informing them which items are closest to expiring

Schema Design of products to sort best before, delimited by tab characters

Category, itemName, size, bestBefore

Category is a field that can be used to sort items based on type, I.e. fridge, beverage etc
itemName is the name of the current item, eg Blue Bird Ready Salted
Size is the quantity of the good contained in the current item, eg 1kg
bestBefore is the date that the current item should be consumed prior to, eg 06 06 2021
