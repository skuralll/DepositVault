# DepositVault

## About

You can pay to protect the chest for a specified amount of time.

## Description

This is a plugin that protects the chest.
With conventional chest protection plug-ins, chests would remain locked semi-permanently even when players stopped coming to the server, which was often a hindrance.  
With this plug-in, you can pay money to lock the chests only for the amount of time you paid for them. After the period, the protection is removed.

## Dependency

- [Vault](https://github.com/MilkBowl/Vault) ＆ [VaultAPI](https://github.com/MilkBowl/VaultAPI).
- Economic plugin that can work with Vault. (I
  recommend [Jeacon](https://github.com/HimaJyun/Jecon))
- DB Server
  
## Usage

The plugin allows many operations to be performed via a GUI.  
To use the GUI, run the command `/dvault` or `/dv`.  
The following screen will open. Click on the item you want to do and follow the instructions.  
![](https://user-images.githubusercontent.com/67959648/233951669-2274f277-744f-4b4a-89e1-a92e63854b5f.jpeg)  

## Configuration
#### config.yml
In config.yml you can configure the following items
|  Item  |  Description  |
| ---- | ---- |
|  version  |  Do not change.  |
|  timezone  |  Your server's time zone ([ref](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones))  |
|  db |  Please enter information about the database to be used.  |
|  lock.unit | Unit of time. (s, m, h, d) |
|  lock.price |  Protection price per unit time  |
|  lock.max | Maximum protection period |
#### messages.yml
You can edit each message in messages.yml. This is also a good place to do localization, etc.
