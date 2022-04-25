package GameClient;

public class GameClientDemo {
    public static void main(String[] args) {
        GameClient gc = new GameClient("127.0.0.1");
        gc.execute();
    }
}
