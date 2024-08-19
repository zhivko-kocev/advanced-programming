package mk.ukim.finki.Labs7;

import java.util.ArrayList;
import java.util.List;

class Song{
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}

interface PlayerState{
    void pressPlay(MP3Player player);
    void pressStop(MP3Player player);
    void pressFWD(MP3Player player);
    void pressREW(MP3Player player);
}

class MP3Player{
    protected PlayerState state;
    protected final List<Song> songs;
    protected int momSong;

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.momSong=0;
        this.state=new StartedState();
    }

    public void pressPlay() {
        state.pressPlay(this);
        state = new PlayingState();
    }


    public void pressStop() {
        state.pressStop(this);
        state = new StoppedState();
    }

    public void pressREW() {
        state.pressREW(this);
        state = new RewardedState();
    }

    public void pressFWD() {
        state.pressFWD(this);
        state = new ForwardedState();
    }

    public void printCurrentSong() {
        System.out.println(songs.get(momSong));
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "songs=" + songs +
                ", momSong=" + momSong +
                '}';
    }
}
class StartedState implements PlayerState{

    @Override
    public void pressPlay(MP3Player player) {
        System.out.printf("Song %d is playing%n",player.momSong);
        player.state = new PlayingState();
    }

    @Override
    public void pressStop(MP3Player player) {

    }

    @Override
    public void pressFWD(MP3Player player) {

    }

    @Override
    public void pressREW(MP3Player player) {

    }
}
class StoppedState implements PlayerState{

    @Override
    public void pressPlay(MP3Player player) {

    }

    @Override
    public void pressStop(MP3Player player) {

    }

    @Override
    public void pressFWD(MP3Player player) {

    }

    @Override
    public void pressREW(MP3Player player) {

    }
}
class ForwardedState implements PlayerState{

    @Override
    public void pressPlay(MP3Player player) {

    }

    @Override
    public void pressStop(MP3Player player) {

    }

    @Override
    public void pressFWD(MP3Player player) {

    }

    @Override
    public void pressREW(MP3Player player) {

    }
}
class RewardedState implements PlayerState{

    @Override
    public void pressPlay(MP3Player player) {

    }

    @Override
    public void pressStop(MP3Player player) {

    }

    @Override
    public void pressFWD(MP3Player player) {

    }

    @Override
    public void pressREW(MP3Player player) {

    }
}
class PlayingState implements PlayerState{

    @Override
    public void pressPlay(MP3Player player) {

    }

    @Override
    public void pressStop(MP3Player player) {

    }

    @Override
    public void pressFWD(MP3Player player) {

    }

    @Override
    public void pressREW(MP3Player player) {

    }
}
public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde