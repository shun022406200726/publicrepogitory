package plugin.sample;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  private int count;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    World world = player.getWorld();
    Location playerLocation = player.getLocation();

    world.spawn(
        new Location(world, playerLocation.getX() + 3, playerLocation.getY(),
            playerLocation.getZ()),
        Camel.class);
  }

  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    List<Color> colorList = List.of(Color.RED, Color.BLUE, Color.PURPLE, Color.WHITE);
    if (count % 2 == 0) {
      // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
      for (Color color : colorList) {
        Player player = e.getPlayer();
        World world = player.getWorld();

        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(color)
                .with(Type.STAR)
                .withFlicker()
                .build());
        fireworkMeta.setPower(0);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);
      }
    }
    count++;
  }
}
