package net.sssssssthedev.SmartClient.ui.gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.sssssssthedev.SmartClient.utils.Checker;
import net.sssssssthedev.SmartClient.utils.ColorUtils;
import org.lwjgl.input.Keyboard;

public class GuiServerFinder extends GuiScreen {
    private static final String[] stateStrings = new String[] { "", ColorUtils.color + "bSearching...", ColorUtils.color + "bResolving...", ColorUtils.color + "cUnknown Host!", ColorUtils.color + "cCancelled!",
            ColorUtils.color + "bDone!", ColorUtils.color + "cAn error occurred" };

    private static GuiMultiplayer prevMenu;

    private GuiTextField ipBox;

    private GuiTextField portBox;

    private GuiTextField maxThreadsBox;

    private int checked;

    private int working;

    private ServerFinderState state;

    enum ServerFinderState {
        NOT_RUNNING, SEARCHING, RESOLVING, UNKNOWN_HOST, CANCELLED, DONE, ERROR;

        public boolean isRunning() {
            return !(this != SEARCHING && this != RESOLVING);
        }

        public String toString() {
            return GuiServerFinder.stateStrings[ordinal()];
        }
    }

    public GuiServerFinder(GuiMultiplayer screen) {
        prevMenu = screen;
    }

    public void updateScreen() {
        this.ipBox.updateCursorCounter();
        this.buttonList.get(0).displayString = this.state.isRunning() ? "Cancel" : "Search";
        this.ipBox.setEnabled(!this.state.isRunning());
        this.portBox.setEnabled(!this.state.isRunning());
        this.maxThreadsBox.setEnabled(!this.state.isRunning());
        this.buttonList.get(0).enabled = (isInteger(this.maxThreadsBox.getText()) &&
                !this.ipBox.getText().isEmpty());
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12 + 16, "Search"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 120 + 12 + 16, "Back"));
        this.ipBox = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, height / 4 + 34, 200, 20);
        this.ipBox.setMaxStringLength(200);
        this.ipBox.setFocused(true);
        this.portBox = new GuiTextField(1, this.fontRendererObj, width / 2 - 32, height / 4 + 76, 40, 12);
        this.portBox.setMaxStringLength(5);
        this.portBox.setFocused(false);
        this.portBox.setText(Integer.toString(25565));
        this.maxThreadsBox = new GuiTextField(1, this.fontRendererObj, width / 2 - 32, height / 4 + 58, 48, 12);
        this.maxThreadsBox.setMaxStringLength(5);
        this.maxThreadsBox.setFocused(false);
        this.maxThreadsBox.setText(Integer.toString(128));
        this.state = ServerFinderState.NOT_RUNNING;
    }

    public void onGuiClosed() {
        this.state = ServerFinderState.CANCELLED;
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton clickedButton) {
        if (clickedButton.enabled)
            if (clickedButton.id == 0) {
                if (this.state.isRunning()) {
                    this.state = ServerFinderState.CANCELLED;
                } else {
                    this.state = ServerFinderState.RESOLVING;
                    this.checked = 0;
                    this.working = 0;
                    (new Thread("Server Finder") {
                        public void run() {
                            try {
                                InetAddress addr =
                                        InetAddress.getByName(GuiServerFinder.this.ipBox.getText().split(":")[0].trim());
                                int port = Integer.parseInt(GuiServerFinder.this.portBox.getText());
                                int[] ipParts = new int[4];
                                for (int i = 0; i < 4; i++)
                                    ipParts[i] = addr.getAddress()[i] & 0xFF;
                                GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.SEARCHING;
                                ArrayList<Checker> pingers = new ArrayList<>();
                                int[] changes = { 0, 1, -1, 2, -2, 3, -3 };
                                int[] arrayOfInt1;
                                int j = (arrayOfInt1 = changes).length;
                                for (int k = 0; k < j; k++) {
                                    int change = arrayOfInt1[k];
                                    for (int i2 = 0; i2 <= 1020; i2++) {
                                        if (GuiServerFinder.this.state == GuiServerFinder.ServerFinderState.CANCELLED)
                                            return;
                                        int[] ipParts2 = ipParts.clone();
                                        ipParts2[2] = ipParts[2] + change & 0xFF;
                                        ipParts2[3] = i2;
                                        String ip = ipParts2[0] + "." + ipParts2[1] + "." + ipParts2[2] + "." +
                                                ipParts2[3];
                                        Checker pinger = new Checker();
                                        pinger.ping(ip, port);
                                        pingers.add(pinger);
                                        while (pingers.size() >=
                                                Integer.parseInt(GuiServerFinder.this.maxThreadsBox.getText())) {
                                            if (GuiServerFinder.this.state == GuiServerFinder.ServerFinderState.CANCELLED)
                                                return;
                                            GuiServerFinder.this.updatePingers(pingers);
                                        }
                                    }
                                }
                                while (pingers.size() > 0) {
                                    if (GuiServerFinder.this.state == GuiServerFinder.ServerFinderState.CANCELLED)
                                        return;
                                    GuiServerFinder.this.updatePingers(pingers);
                                }
                                GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.DONE;
                            } catch (UnknownHostException e) {
                                GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.UNKNOWN_HOST;
                            } catch (Exception e) {
                                e.printStackTrace();
                                GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.ERROR;
                            }
                        }
                    }).start();
                }
            } else if (clickedButton.id == 2) {
                mc.displayGuiScreen(new GuiTools(prevMenu));
            }
    }

    private boolean serverInList(String ip) {
        for (int i = 0; i < GuiMultiplayer.savedServerList.countServers(); i++) {
            if ((GuiMultiplayer.savedServerList.getServerData(i)).serverIP.equals(ip))
                return true;
        }
        return false;
    }

    private void updatePingers(ArrayList<Checker> pingers) {
        for (int i = 0; i < pingers.size(); i++) {
            if (!pingers.get(i).isStillPinging()) {
                this.checked++;
                if (pingers.get(i).isWorking()) {
                    this.working++;
                    if (!serverInList(pingers.get(i).server.serverIP)) {
                        GuiMultiplayer.savedServerList.addServerData(new ServerData("Chcked #" + this.working,
                                pingers.get(i).server.serverIP, false));
                        GuiMultiplayer.savedServerList.saveServerList();
                        prevMenu.serverListSelector.setSelectedSlotIndex(-1);
                        prevMenu.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
                    }
                }
                pingers.remove(i);
            }
        }
    }

    protected void keyTyped(char par1, int par2) {
        this.ipBox.textboxKeyTyped(par1, par2);
        this.maxThreadsBox.textboxKeyTyped(par1, par2);
        this.portBox.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156)
            actionPerformed(this.buttonList.get(0));
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        this.ipBox.mouseClicked(par1, par2, par3);
        this.maxThreadsBox.mouseClicked(par1, par2, par3);
        this.portBox.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Server Finder", width / 2, 20, 16777215);
        drawCenteredString(this.fontRendererObj, "This will search for servers with similar IPs", width / 2, 40,
                10526880);
        drawCenteredString(this.fontRendererObj, "to the IP you type into the field below.", width / 2, 50, 10526880);
        drawCenteredString(this.fontRendererObj, "The servers it finds will be added to your server list.", width / 2,
                60, 10526880);
        drawString(this.fontRendererObj, "Server address:", width / 2 - 100, height / 4 + 24, 10526880);
        this.ipBox.drawTextBox();
        this.portBox.drawTextBox();
        drawString(this.fontRendererObj, "Max. threads:", width / 2 - 100, height / 4 + 60, 10526880);
        this.maxThreadsBox.drawTextBox();
        drawString(this.fontRendererObj, "Port number:", width / 2 - 100, height / 4 + 60 + 20, 10526880);
        drawCenteredString(this.fontRendererObj, this.state.toString(), width / 2, height / 4 + 1, 10526880);
        drawString(this.fontRendererObj, "Checked: " + this.checked + " / 7147", width / 2 - 100, height / 4 + 84 + 16,
                10526880);
        drawString(this.fontRendererObj, "Working: " + this.working, width / 2 - 100, height / 4 + 94 + 16, 10526880);
        super.drawScreen(par1, par2, par3);
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

