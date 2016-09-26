/**
 *  HomeTeleport
 *  Copyright (C) 2016 Sindastra. All rights reserved.
 *  https://github.com/sindastra/HomeTeleport
 * 
 * HomeTeleport is a simple Bukkit plugin to teleport a player home to their bed.
 * 
 * The above copyright notice and this notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 * @author Sindastra <sindastra+github@gmail.com>
 * @copyright (C) 2016 Sindastra. All rights reserved.
 */

package com.github.sindastra.HomeTeleport;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private boolean lightningOnDeparture;
	private boolean lightningOnArrival;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		lightningOnDeparture = getConfig().getBoolean("lightning-on-departure");
		lightningOnArrival = getConfig().getBoolean("lightning-on-arrival");
		getLogger().info("Enabled.");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled.");
	}
	
	@Override
	public boolean onCommand( CommandSender sender , Command cmd , String label , String[] args ) {
		if( cmd.getName().equalsIgnoreCase("home") ) {
			if( !( sender instanceof Player ) )
				sender.sendMessage("This command must be run by a player.");
			else {
				Player player = (Player) sender;
				Location bedLoc = (Location) player.getBedSpawnLocation();
				if( bedLoc != null )
				{
					if(lightningOnDeparture)
						player.getWorld().strikeLightningEffect(player.getLocation());
					
					player.teleport(bedLoc);
					
					if(lightningOnArrival)
						player.getWorld().strikeLightningEffect(bedLoc);
				}
				else
					player.sendMessage( ChatColor.RED + "No bed found!" );
			}
			return true;
		}		
		return false;
	}

}
