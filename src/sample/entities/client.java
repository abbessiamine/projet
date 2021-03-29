package sample.entities;

public class client {


    private int id;
    private int ticket;
    private String mail;

    public client(int id, int ticket) {
        this.id = id;
        this.ticket = ticket;
    }

    public client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
