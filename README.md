# Breath of Dawn
 Breath of Dawn is a minecraft server project that aims to bring a new and innovative 
 MMORPG experience to players. This repo contains the source code to the primary BoD plugin, along with
 other non-mandatory tool plugins. 
 
## Development Guide

1. Install Java Dev Kit [JDK](https://www.oracle.com/java/technologies/downloads/#jdk19-windows)

2. Install Java IDE [IntelliJ](https://www.jetbrains.com/idea/)
   - On the IntelliJ welcome screen select "plugins" and search for "minecraft" in the marketplace.<br>Install the "Minecraft Development" plugin.
 
3. On the IntelliJ welcome screen hit Get from VCS and copy this repo.

### To build the jar file
Access the project artifact settings
```
ctrl + alt + shift + S
```
OR<br>
```
Navigate to File -> Project Structure -> Artifacts
```
Select JAR -> From module with dependencies, leave defaults. Modify output directory to desired location and hit apply.
```
Build -> Build artifacts -> Build (I recomend you hotkey this)
```

## Paper server setup

1. Install [PaperMC](https://papermc.io/downloads)
2. Create a .bat file with the following contents in the same directory as your PaperMC jar.
   - ```
     java -Xms1G -Xmx5G -jar paper.jar nogui GOTO start
     ```
3. Run the bat file and accept Minecraft EULA.
