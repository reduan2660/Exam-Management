package dev.examsmanagement.modal;

public class Question {
    private int id;
    private String question;
    private int points=1;

    Question(String _question,int _points){
        question = _question;
        points = _points;
    }

    Question(int _id, String _question, int _points){
        id = _id;
        question = _question;
        points = _points;
    }
}
