import java.util.Scanner;

class Song {
    String title;
    String artist;
    Song next;
    Song prev;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.next = null;
        this.prev = null;
    }
}

class Playlist {
    Song head; //Pointer paling atas atau head untuk playlist
    Song tail; //Pointer paling bawah atau tail untuk playlist
    Song currentSong;

    public void addSong(String title, String artist) {
        Song newSong = new Song(title, artist);
        if (head == null) {
            head = tail= newSong;
            currentSong = head;
        } else {
            tail.next = newSong;
            newSong.prev = tail;
            tail = newSong; //update tail ke node baru
        }
        System.out.println("'" + title + "' has been added to the playlist.");
    }

    public void play() {
        if (currentSong != null) {
            System.out.println("--> Now Playing " + currentSong.title + " - " + currentSong.artist);
        } else {
            System.out.println("Playlist has ended or is empty.");
        }
    }

    public void nextSong() {
        if (currentSong != null && currentSong.next != null) {
            currentSong = currentSong.next;
        } else {
            System.out.println("You are at the end of the playlist.");
        }
    }

    public void previousSong() {
        if (currentSong != null && currentSong.next != null) {
            currentSong = currentSong.prev;
        } else {
            System.out.println("You are at the beginning of a playlist");
        }
    }

    public void printPlaylist() {
        System.out.println("\n--- Your Playlist ---");
        Song temp = head;
        if (temp == null) {
            System.out.println("Playlist is empty.");
        }
        while (temp != null) {
            System.out.println("- " + temp.title + " by " + temp.artist);
            temp = temp.next;
        }
        System.out.println("------------------------\n");
    }

    public void Replay() {
        if (head == null) {
            System.out.println("Your playlist is empty.");
        } else {
            currentSong = head;
            System.out.println("Playlist will replay from beginning.");
        }
    }

    public static void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Playlist myPlaylist = new Playlist();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        myPlaylist.addSong("Bohemian Rhapsody", "Queen");
        myPlaylist.addSong("The Contract", "Twenty One Pilots");
        myPlaylist.addSong("Scars", "Novulent");

        while (!exit) {
            System.out.println("\nMenu");
            System.out.println("1. Add a new song");
            System.out.println("2. Play current song");
            System.out.println("3. Play next song");
            System.out.println("4. Play previous song");
            System.out.println("5. Show full playlist");
            System.out.println("6. Replay playlist");
            System.out.println("7. Exit");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter song title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter artist name: ");
                    String artist = scanner.nextLine();
                    myPlaylist.addSong(title, artist);
                    simulateDelay(1000);
                    break;
                case 2:
                    myPlaylist.play();
                    simulateDelay(1000);
                    break;
                case 3:
                    myPlaylist.nextSong();
                    myPlaylist.play(); 
                    break;
                case 4:
                    myPlaylist.previousSong();
                    myPlaylist.play();
                case 5:
                    myPlaylist.printPlaylist();
                    break;
                case 6:
                    myPlaylist.Replay();
                    myPlaylist.play();
                case 7:
                    exit = true;
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");   
            }
        }
        scanner.close();
    }
}
