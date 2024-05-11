package RushPro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class rushProBadlion {
    private JFrame frame;
    private JLabel mapLabel;
    private JLabel speedLabel;
    private JLabel blocksLabel;
    private Map<String, String> mapGenSpeed; // Map to store generation speed
    private Map<String, String> mapBlocks; // Map to store block count
    
    public rushProBadlion() {        
        frame = new JFrame("Map Info (Badlion)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 110); // Increased height to accommodate two lines
        frame.setUndecorated(true); // Title bar visibility
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width - frame.getWidth(), 0);
        
        //close button
        String userHome = System.getProperty("user.home");
        
        ImageIcon icon = new ImageIcon(userHome + "\\.mapInfo\\closeButton.png");
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(30, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.setVisible(true);
        frame.setOpacity((float) 0.6);

        // Make the JFrame draggable
        final Point offset = new Point();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offset.x = e.getX();
                offset.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - offset.x;
                int y = e.getYOnScreen() - offset.y;
                frame.setLocation(x, y);
            }
        });
        
        frame.setVisible(true);
        String userHome1 = System.getProperty("user.home");
        System.out.println(userHome1);
        
        String logFileName = "latest.log";
        String logFilePath = userHome1 + File.separator + "AppData" + File.separator  + "Roaming" + File.separator + ".minecraft" + File.separator + "logs" + File.separator + "blclient" + File.separator + "minecraft" + File.separator + logFileName;

        System.out.println("Log file path: " + logFilePath);
        panel = new JPanel();
        frame.add(panel);

        // Set a custom font (e.g., "Arial") for the labels
        Font customFont = new Font("Monospaced", Font.BOLD, 14);

        mapLabel = new JLabel("Loading...");
        mapLabel.setFont(customFont);
        panel.add(mapLabel);

        speedLabel = new JLabel();
        speedLabel.setFont(customFont);
        panel.add(speedLabel);

        blocksLabel = new JLabel();
        blocksLabel.setFont(customFont);
        panel.add(blocksLabel);

        // Initialize map data
        initializeMapData();

        // Schedule a task to update the map name every 3 seconds
        Timer timer = new Timer(3000, e -> {
            updateMapName();
        });
        timer.start();
    }

	private void initializeMapData() {
        // Initialize map data (map name -> generation speed and block count)
        mapGenSpeed = new HashMap<>();
        mapBlocks = new HashMap<>();
        
        //rush direction
        mapGenSpeed.put("Acropolis","Side");
        mapGenSpeed.put("Aetius", "Side");
        mapGenSpeed.put("Airshow","Diamond");
        mapGenSpeed.put("Amazon", "Side");
        mapGenSpeed.put("Ambush","Diamond");
        mapGenSpeed.put("Apollo","Diamond");
        mapGenSpeed.put("Aqil","Side");
        mapGenSpeed.put("Arcade","Side");
        mapGenSpeed.put("Arid","Side");
        mapGenSpeed.put("Ashfire","Back diagonal");
        mapGenSpeed.put("Bio-Hazard","Side");
        mapGenSpeed.put("Blossom","Diamond");
        mapGenSpeed.put("Cascade","Side");
        mapGenSpeed.put("Casita","Side");
        mapGenSpeed.put("Cliffside","Diamond");
        mapGenSpeed.put("Crogorm","Back diagonal");
        mapGenSpeed.put("Crypt","Semi-mid");
        mapGenSpeed.put("Deadwood","Diamond");
        mapGenSpeed.put("Dockyard","Side");
        mapGenSpeed.put("Dragon Light","TBD");
        mapGenSpeed.put("Dragonstar","Side");
        mapGenSpeed.put("Gateway","Side");
        mapGenSpeed.put("Glacier","Side");
        mapGenSpeed.put("Hanging Gardens","Side");
        mapGenSpeed.put("Harvest","Side");
        mapGenSpeed.put("Hollow","Side");
        mapGenSpeed.put("Impere","Forward");
        mapGenSpeed.put("Ironclad","Side");
        mapGenSpeed.put("Keep","Diamond");
        mapGenSpeed.put("Lighthouse","Side");
        mapGenSpeed.put("Lightstone","Side");
        mapGenSpeed.put("Lotus","Side");
        mapGenSpeed.put("Lucky Rush","Side");
        mapGenSpeed.put("Meso","Side");
        mapGenSpeed.put("Mirage","Side");
        mapGenSpeed.put("Nebuc","Side");
        mapGenSpeed.put("Orbit","Side");
        mapGenSpeed.put("Orchestra","Side");
        mapGenSpeed.put("Pavilion","Side");
        mapGenSpeed.put("Pernicious","Who knows");
        mapGenSpeed.put("Playground","Diamond");
        mapGenSpeed.put("Polygon","Side");
        mapGenSpeed.put("Rooftop","Diamond");
        mapGenSpeed.put("Rooted","Back diagonal");
        mapGenSpeed.put("Sanctum","Side");
        mapGenSpeed.put("Scorched Sands","Side");
        mapGenSpeed.put("Serenity","Side");
        mapGenSpeed.put("Siege","Side");
        mapGenSpeed.put("Sky Rise","Diamond");
        mapGenSpeed.put("Slumber","Side");
        mapGenSpeed.put("Solace","Side");
        mapGenSpeed.put("Speedway","Diamond");
        mapGenSpeed.put("Steampunk","Back diagonal");
        mapGenSpeed.put("Toro","Side");
        mapGenSpeed.put("Tuzi","Side");
        mapGenSpeed.put("Urban Plaza","Forward");
        mapGenSpeed.put("Vigilante","Side");
        mapGenSpeed.put("Waterfall","Side");
        mapGenSpeed.put("Yue","Side");
        mapGenSpeed.put("Zarzul","Side");
        //Badlion port
        mapGenSpeed.put("Bio","Side");
        mapGenSpeed.put("Dragon","TBD");
        mapGenSpeed.put("Hanging","Side");
        mapGenSpeed.put("Lucky","Side");
        mapGenSpeed.put("Scorched","Side");
        mapGenSpeed.put("Sky","Diamond");
        mapGenSpeed.put("Urban","Forward");
        
        //blocks needed
        mapBlocks.put("Acropolis","64");
        mapBlocks.put("Aetius","64");
        mapBlocks.put("Airshow","48");
        mapBlocks.put("Amazon","48");
        mapBlocks.put("Ambush","64");
        mapBlocks.put("Apollo","64");
        mapBlocks.put("Aqil","32");
        mapBlocks.put("Arcade","48");
        mapBlocks.put("Arid","64");
        mapBlocks.put("Ashfire","48");
        mapBlocks.put("Bio-Hazard","48");
        mapBlocks.put("Blossom","64");
        mapBlocks.put("Cascade","64");
        mapBlocks.put("Casita","64");
        mapBlocks.put("Cliffside","64");
        mapBlocks.put("Crogorm","48");
        mapBlocks.put("Crypt","32");
        mapBlocks.put("Deadwood","16");
        mapBlocks.put("Dockyard","64");
        mapBlocks.put("Dragon Light","64");
        mapBlocks.put("Dragonstar","32-48");
        mapBlocks.put("Gateway","64");
        mapBlocks.put("Glacier","48");
        mapBlocks.put("Hanging Gardens","64");
        mapBlocks.put("Harvest","64");
        mapBlocks.put("Hollow","32-48");
        mapBlocks.put("Impere","64");
        mapBlocks.put("Ironclad","64");
        mapBlocks.put("Keep","64");
        mapBlocks.put("Lighthouse","48");
        mapBlocks.put("Lightstone","32");
        mapBlocks.put("Lotus","48");
        mapBlocks.put("Lucky Rush","64");
        mapBlocks.put("Meso","64");
        mapBlocks.put("Mirage","64");
        mapBlocks.put("Nebuc","64");
        mapBlocks.put("Orbit","64");
        mapBlocks.put("Orchestra","32-48");
        mapBlocks.put("Pavilion","64");
        mapBlocks.put("Pernicious","48");
        mapBlocks.put("Playground","64");
        mapBlocks.put("Polygon","32-48");
        mapBlocks.put("Rooftop","48-64");
        mapBlocks.put("Rooted","64");
        mapBlocks.put("Sanctum","64");
        mapBlocks.put("Scorched Sands","64");
        mapBlocks.put("Serenity","64");
        mapBlocks.put("Siege","64");
        mapBlocks.put("Sky Rise","64");
        mapBlocks.put("Slumber","48");
        mapBlocks.put("Solace","32");
        mapBlocks.put("Speedway","48");
        mapBlocks.put("Steampunk","64");
        mapBlocks.put("Toro","48-64");
        mapBlocks.put("Tuzi","64");
        mapBlocks.put("Urban Plaza","64");
        mapBlocks.put("Vigilante","64");
        mapBlocks.put("Waterfall","48");
        mapBlocks.put("Yue","64");
        mapBlocks.put("Zarzul","48");
        //Badlion port			
        mapBlocks.put("Bio","48");
        mapBlocks.put("Dragon","64");
        mapBlocks.put("Hanging","64");
        mapBlocks.put("Lucky","64");
        mapBlocks.put("Scorched","64");
        mapBlocks.put("Sky","64");
        mapBlocks.put("Urban","64");
        
        //seasonal maps
        //lunar new year
        mapBlocks.put("Lunarhouse","48");
        mapGenSpeed.put("Lunarhouse","");
        //Easter
        mapBlocks.put("Bloom","");
        mapGenSpeed.put("Bloom","");
        mapBlocks.put("Easter Basket","");
        mapGenSpeed.put("Easter Basket","");
        mapBlocks.put("Easter Garden","");
        mapGenSpeed.put("Easter Garden","");
        mapBlocks.put("Egg Hunt","");
        mapGenSpeed.put("Egg Hunt","");
        mapBlocks.put("Meadow","");
        mapGenSpeed.put("Meadow","");
        mapBlocks.put("Sunflower","");
        mapGenSpeed.put("Sunflower","");
        //Summer
        mapBlocks.put("Fruitbrawl","");
        mapGenSpeed.put("Fruitbrawl","");
        mapBlocks.put("Gelato","");
        mapGenSpeed.put("Gelato","");
        mapBlocks.put("Montipora","");
        mapGenSpeed.put("Montipora","");
        mapBlocks.put("Symphonic","32-48");
        mapGenSpeed.put("Symphonic","");
        //Halloween
        mapBlocks.put("Darkened","");
        mapGenSpeed.put("Darkened","");
        mapBlocks.put("Ghoulish","");
        mapGenSpeed.put("Ghoulish","");
        mapBlocks.put("Ominosity","48");
        mapGenSpeed.put("Ominosity","Back diagonal");
        mapBlocks.put("Scareshow","48");
        mapGenSpeed.put("Scareshow","Diamond");
        mapBlocks.put("Screamway","48");
        mapGenSpeed.put("Screamway","Diamond");
        mapBlocks.put("Steampumpkin","64");
        mapGenSpeed.put("Steampumpkin","Back diagonal");
        mapBlocks.put("Trick or Yeet","");
        mapGenSpeed.put("Trick or Yeet","");
        //Holidays
        mapBlocks.put("Blitzen","48");
        mapGenSpeed.put("Blitzen","Side");
        mapBlocks.put("Fireplace","32");
        mapGenSpeed.put("Fireplace","Side");
        mapBlocks.put("Frosted","");
        mapGenSpeed.put("Frosted","");
        mapBlocks.put("Lotice","48");
        mapGenSpeed.put("Lotice","Side");
        mapBlocks.put("Nutcracker","64");
        mapGenSpeed.put("Nutcracker","Diamond");
        mapBlocks.put("Snowy Square","");
        mapGenSpeed.put("Snowy Square","");
        mapBlocks.put("Sweet Wonderland","");
        mapGenSpeed.put("Sweet Wonderland","");
        //badlion port
        mapBlocks.put("Egg Hunt","");
        mapGenSpeed.put("Egg","");
        mapBlocks.put("Trick or Yeet","");
        mapGenSpeed.put("Trick","");
        mapBlocks.put("Snowy Square","");
        mapGenSpeed.put("Snowy","");
        mapBlocks.put("Sweet Wonderland","");
        mapGenSpeed.put("Sweet","");
    }


	private void updateMapName() {
        String userHome = System.getProperty("user.home");
        String logFileName = "latest.log";
        String logFilePath = userHome + File.separator + "AppData" + File.separator
                + "Roaming" + File.separator + ".minecraft" + File.separator + "logs" + File.separator + "blclient" + File.separator + "minecraft" + File.separator + logFileName;
        
        // Define a regular expression pattern to match the new log output
        Pattern mapPattern = Pattern.compile("You are currently playing on (\\w+)");

        // Initialize the currentMap variable
        String currentMap = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Search for the map name in each line
                Matcher matcher = mapPattern.matcher(line);
                if (matcher.find()) {
                    currentMap = matcher.group(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the labels with the extracted map data
        if (currentMap != null && mapGenSpeed.containsKey(currentMap) && mapBlocks.containsKey(currentMap)) {
            String genSpeed = mapGenSpeed.get(currentMap);
            String blockCount = mapBlocks.get(currentMap);
            mapLabel.setText("Map: " + currentMap + ",");
            speedLabel.setText("Rush direction: " + genSpeed + ",");
            blocksLabel.setText("Blocks: " + blockCount);
        } else {
            mapLabel.setText("Map data not found for " + currentMap);
            speedLabel.setText("");
            blocksLabel.setText("");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(rushProBadlion::new);
    }
}
