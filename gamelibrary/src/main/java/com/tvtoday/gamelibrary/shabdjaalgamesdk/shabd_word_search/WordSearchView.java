package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabd_word_search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;

import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalBoradItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Work Pending
 * *Is All Done -D
 * *Hint- D
 * *State Save
 * *Multiple Color- D
 * Strikethrough for searched word- D
 * timer- D
 */
public class WordSearchView extends View {

    private int rows, columns, width, height, rectWH;

    //   private char[][] letters;
    private Set<Word> words = new HashSet<>();

    private Cell[][] cells;
    private Cell cellDragFrom, cellDragTo;

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint highlighterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint gridLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint hintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Typeface typeface;

    private OnWordSearchedListener onWordSearchedListener;
    private int wordsSearched = 0;
    private int[] highlighterColors = {0x4400649C, 0x44ffd900, 0x447fbb00};
    private int[] highlighterColors2 = {0x4400009C, 0x4488d900, 0x447fbb99,
            0x44006411, 0x44ff2222, 0x4422bb55,
            0x44006422, 0x44ff6600, 0x4455bb00,
            0x44006433, 0x44ff8800, 0x4477bb00,
            0x44006444, 0x44ff9900, 0x4488bb00,
            0x44006455, 0x44ff0000};

    private ArrayList<ShabdjaalBoradItem> shabdjaalBoradItem;
    private ArrayList<String> splitedList;
    private List<String> wordsList;
    private int[] answerStatus;

    public WordSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setSubpixelText(true);
        textPaint.setColor(0xcc000000);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.LEFT);
        if(typeface != null) {
            textPaint.setTypeface(typeface);
        }
//        textPaint.setAlpha(foregroundOpacity);

        highlighterPaint.setStyle(Paint.Style.STROKE);
        highlighterPaint.setStrokeWidth(80);
        highlighterPaint.setStrokeCap(Paint.Cap.ROUND);
        highlighterPaint.setColor(0x44ffd900);

        gridLinePaint.setStyle(Paint.Style.STROKE);
        gridLinePaint.setStrokeWidth(4);
        gridLinePaint.setStrokeCap(Paint.Cap.SQUARE);
        gridLinePaint.setColor(0x11000000);

        hintPaint.setColor(0xccff0000);
        hintPaint.setTextSize(50);
        hintPaint.setTextAlign(Paint.Align.LEFT);
        if(typeface != null) {
            hintPaint.setTypeface(typeface);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        try{
            if(rows <= 0 || columns <= 0) {
                return;
            }

            // draw grid
            for(int i = 0; i < rows - 1; i++) {
                canvas.drawLine(0, cells[i][0].getRect().bottom, width, cells[i][0].getRect().bottom, gridLinePaint);
            }

            for(int i = 0; i < columns - 1; i++) {
                canvas.drawLine(cells[0][i].getRect().right, cells[0][0].getRect().top, cells[0][i].getRect().right, cells[columns-1][0].getRect().bottom, gridLinePaint);
            }

            // draw letters
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    String letter = String.valueOf(cells[i][j].getLetter());
                    boolean isHinted = cells[i][j].isHinted();
                    boolean isAnswered = cells[i][j].isAnswered();
                    Rect textBounds = new Rect();
                    if(isHinted & !isAnswered ){
                        hintPaint.getTextBounds(letter, 0, 1, textBounds);
                        canvas.drawText(letter, cells[i][j].getRect().centerX() - (hintPaint.measureText(letter) / 2),
                                cells[i][j].getRect().centerY() + (textBounds.height() / 2), hintPaint);
                    }else {
                        textPaint.getTextBounds(letter, 0, 1, textBounds);
                        canvas.drawText(letter, cells[i][j].getRect().centerX() - (textPaint.measureText(letter) / 2),
                                cells[i][j].getRect().centerY() + (textBounds.height() / 2), textPaint);
                    }
                }
            }
            int count =0;

            // draw highlighter
            if(cellDragFrom != null && cellDragTo != null && isFromToValid(cellDragFrom, cellDragTo)) {
                highlighterPaint.setColor(highlighterColors[count%3]);
                canvas.drawLine(cellDragFrom.getRect().centerX(), cellDragFrom.getRect().centerY(),
                        cellDragTo.getRect().centerX() + 1, cellDragTo.getRect().centerY(), highlighterPaint);
            }

            if(words != null && words.size() > 0){
                count = 0;
                for(Word word : words) {
                    Log.e("bee_",word.toString());
                    count = count+1;
                    if(word.isHighlighted()) {
                        highlighterPaint.setColor(highlighterColors2[highlighterColors2.length - count%20]);
                        canvas.drawLine(
                                cells[word.getFromRow()][word.getFromColumn()].getRect().centerX(),
                                cells[word.getFromRow()][word.getFromColumn()].getRect().centerY(),
                                cells[word.getToRow()][word.getToColumn()].getRect().centerX() + 1,
                                cells[word.getToRow()][word.getToColumn()].getRect().centerY(), highlighterPaint);
                    }
                }
            }
        }catch (Exception e){

        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);
        int d = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(d, d);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        initCells();
    }

    private void initCells() {

        if(rows <= 0 || columns <= 0) {
            return;
        }
        cells = new Cell[rows][columns];
        rectWH = width/rows;

        if(shabdjaalBoradItem != null && !shabdjaalBoradItem.isEmpty()){
            int count = 0;
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    if(count < shabdjaalBoradItem.size()){
                        cells[i][j] = new Cell(new Rect(j*rectWH,i*rectWH,(j+1)*rectWH,(i+1)*rectWH),shabdjaalBoradItem.get(count).getLetter(),i,j, shabdjaalBoradItem.get(count).isHinted(), shabdjaalBoradItem.get(count).isInWord());
                        count = count +1;
                    }
                }
            }
        }
    }

    private int measure(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.UNSPECIFIED) {
            result = 100;
        } else {
            result = specSize;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(wordsList !=null && !wordsList.isEmpty()){
            final int pointerIndex = MotionEventCompat.getActionIndex(event);
            final float x = MotionEventCompat.getX(event, pointerIndex);
            final float y = MotionEventCompat.getY(event, pointerIndex);

//        Log.d("WordsGrid", "x:" + x + ", y:" + y);

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                cellDragFrom = getCell(x,y);
                cellDragTo = cellDragFrom;
                invalidate();
            } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                Cell cell = getCell(x,y);
                if(cellDragFrom != null && cell != null && isFromToValid(cellDragFrom, cell)) {
                    cellDragTo = cell;
                    invalidate();
                }
            } else if(event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("WordsGrid", getWordStr(cellDragFrom, cellDragTo));
                String word = getWordStr(cellDragFrom, cellDragTo);
                Log.d("wordTest", word.toString());

                if(wordsList.contains(new StringBuilder(word).reverse().toString())){
                    word = new StringBuilder(word).reverse().toString();
                    Log.e("www",word.toString());
                }
                if(wordsList.contains(word) || wordsList.contains(new StringBuilder(word).reverse().toString())){
                    if(Integer.valueOf(cellDragFrom.getRow()) !=null && Integer.valueOf(cellDragFrom.getColumn()) !=null){
                        Word word1 = new Word(word, true, cellDragFrom.getRow(), cellDragFrom.getColumn(), cellDragTo.getRow(), cellDragTo.getColumn());
                        Log.e("ashas",word1.toString());
                        words.add(word1);
                    }


                   /* for (int i = cellDragFrom.getRow(); i <=cellDragTo.getRow() ; i++) {
                        for (int j = cellDragFrom.getColumn(); j <=cellDragTo.getColumn() ; j++) {
                            cells[i][j].setAnswered(true);
                            cells[i][j].setHinted(true);
                        }
                    }*/

                    try{
                        Cell from = cellDragFrom;
                        Cell to = cellDragTo;
                        if(Integer.valueOf(from.getRow()) != null){
                            if(from.getRow() == to.getRow()) {

                                if(from.getColumn() < to.getColumn()){
                                    int c = from.getColumn() < to.getColumn() ? from.getColumn() : to.getColumn();
                                    for(int i = 0; i < Math.abs(from.getColumn() - to.getColumn()) + 1; i++) {
                                        cells[from.getRow()][i+c].setAnswered(true);
                                        cells[from.getRow()][i+c].setHinted(true);
                                    }
                                }else {
                                    // int c = from.getColumn() < to.getColumn() ? from.getColumn() : to.getColumn();
                                    for(int i = from.getColumn(); i > to.getColumn()-1; i--) {
                                        cells[from.getRow()][i].setAnswered(true);
                                        cells[from.getRow()][i].setHinted(true);
                                    }
                                }

                            } else if(from.getColumn() == to.getColumn()) {

                                int r = from.getRow() < to.getRow() ? from.getRow() : to.getRow();
                                for(int i = 0; i < Math.abs(from.getRow() - to.getRow()) + 1; i++) {
                                    cells[i+r][to.getColumn()].setAnswered(true);
                                    cells[i+r][to.getColumn()].setHinted(true);
                                }
                            }else if(from.getRow() > to.getRow() && from.getColumn() > to.getColumn()){

                                int fRow= from.getRow();
                                int fCol = from.getColumn();

                                while (fRow > to.getRow()-1 && fCol > to.getColumn()-1){

                                    cells[fRow][fCol].setAnswered(true);
                                    cells[fRow][fCol].setHinted(true);
                                    fRow--;
                                    fCol--;
                                    Log.e("Checking_word",word);
                                }
                   /* for(int i=from.getRow(); i>to.getRow()-1; i--){
                        for(int j = from.getColumn(); j > to.getColumn()-1; j--){
                            word += String.valueOf(cells[i][j].getLetter());
                            Log.e("Checking_word",word);
                        }
                    }*/

                            }else if(from.getRow() > to.getRow() && from.getColumn() < to.getColumn()){
                                int fRow= from.getRow();
                                int fCol = from.getColumn();

                                while (fRow > to.getRow()-1 && fCol < to.getColumn()+1){

                                    cells[fRow][fCol].setAnswered(true);
                                    cells[fRow][fCol].setHinted(true);
                                    fRow--;
                                    fCol++;
                                    Log.e("Checking_word",word);
                                }
                            }else if(from.getRow() > to.getRow() && from.getColumn() == to.getColumn()){
                                if(from.getRow() > to.getRow()){
                                    Toast.makeText(getContext().getApplicationContext(), "Hello Javatpoint",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                if(from.getRow() > to.getRow()) {
                                    Cell cell = from;
                                    from = to;
                                    to = cell;
                                }
                                for(int i = 0; i < Math.abs(from.getRow() - to.getRow()) + 1; i++) {
                                    int foo = from.getColumn() < to.getColumn() ? i : -i;
                                    cells[from.getRow()+i][from.getColumn()+foo].setAnswered(true);
                                    cells[from.getRow()+i][from.getColumn()+foo].setHinted(true);
                                }
                            }
                        }
                    }catch (Exception e){

                    }


                }
                else{
                    if(onWordSearchedListener != null){
                        onWordSearchedListener.wordNotFound();
                    }
                }
                highlightIfContain(word);

                cellDragFrom = null;
                cellDragTo = null;
                invalidate();
                return false;
            }
        }
        return true;
    }

    private Cell getCell(float x, float y) {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(cells[i][j].getRect().contains((int)x,(int)y)) {
                    return cells[i][j];
                }
            }
        }
        return null;
    }

   /* public void setLetters(char[][] letters) {
        this.letters = letters;
        rows = letters.length;
        columns = letters[0].length;
        initCells();
        invalidate();
    }*/

    public void setLetters(ArrayList<ShabdjaalBoradItem> shabdjaalBoradItem){
        this.shabdjaalBoradItem = shabdjaalBoradItem;
        rows = 11;
        columns = 11;
        initCells();
        invalidate();
    }

    public void forRevealWord(){
        int index = getUnAnswerdWord();
        if(index == -1){
            return;
        }

        String[] splitWord = splitedList.get(index).split(",");
        String answer = wordsList.get(index);

        //String answer = "विमलराय";
        //  String answer = "गुरुदत्त";
        //  Log.e("xys",splitedList.toString());
        //String[] splitWord = "वि,म,ल,रा,य".split(",");
        //  String[] splitWord = "गु,रु,द,त्त".split(",");
        //String[] splitWord = "वऋ,षि,के,श,मु,ख,र्जी".split(",");

        int frow= -1;
        int fcol = -1;
        //for last cell
        int trow = -1;
        int tcol = -1;
        // cells = new Cell[rows][columns];

        for(int k=0; k<splitWord.length; k++){

            // Log.d("cellssss", splitWord[2]);
            for(int i=0 ; i<rows; i++){
                for(int j =0; j <columns ; j++){

                    if(cells[i][j].getLetter().equals(splitWord[0])){
                        int c = 0;

                        int countArr[] = {0,0,0,0,0,0,0,0};

                     /*  int count1=0;
                       int count2=0;
                       int count3=0;
                       int count4=0;
                       int count5=0;
                       int count6=0;
                       int count7=0;
                       int count8=0;*/

                        while (c<splitWord.length){

                            if(j+c<columns){
                                //left to right
                                if(cells[i][j+c].getLetter().equals(splitWord[c])){
                                    //count1+=1;
                                    countArr[0] +=1;
                                }

                                if(countArr[0] ==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    frow= i;
                                    fcol = j;

                                    trow= i;
                                    tcol = j+c;
                                    Log.d("ccaa",i+"--------"+(j+c));
                                    break;
                                }
                            }

                            if(i+c<rows){
                                //top to bottom
                                if(cells[i+c][j].getLetter().equals(splitWord[c])){
                                    //count2+=1;
                                    countArr[1] +=1;
                                }

                                if(countArr[1]==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    frow= i;
                                    fcol = j;

                                    trow= i+c;
                                    tcol = j;
                                    Log.d("ccaa",(i+c)+"--------"+(j));
                                    break;
                                }
                            }
                            if(i+c <rows && j+c <columns){

                                if((cells[i+c][j+c].getLetter().equals(splitWord[c]))){

                                    //count3+=1;
                                    countArr[2] +=1;
                                }
                                if(countArr[2] ==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    Log.d("ccaa",(i+c)+"--------"+(j+c));
                                    frow= i;
                                    fcol = j;

                                    trow= i+c;
                                    tcol = j+c;

                                    break;
                                }
                            }

                            if(j-c>=0){
                                if(cells[i][j-c].getLetter().equals(splitWord[c])){
                                    //count4+=1;
                                    countArr[3] +=1;
                                }

                                if(  countArr[3]==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    Log.d("ccaa",(i)+"--------"+(j-c));
                                    frow= i;
                                    fcol = j;

                                    trow= i;
                                    tcol = j-c;
                                    break;
                                }
                            }

                            if(i-c >=0){
                                if(cells[i-c][j].getLetter().equals(splitWord[c])){
                                    //count5+=1;
                                    countArr[4] +=1;
                                }
                                if(countArr[4] ==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    Log.d("ccaa",(i-c)+"--------"+(j));
                                    frow= i;
                                    fcol = j;

                                    trow= i-c;
                                    tcol = j;
                                    break;
                                }
                            }

                            if(i-c >=0 && j-c >=0){
                                if(cells[i-c][j-c].getLetter().equals(splitWord[c])){
                                    countArr[5] +=1;
                                }

                                if(countArr[5] ==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    Log.d("ccaa",(i-c)+"--------"+(j-c));

                                    frow= i;
                                    fcol = j;

                                    trow= i-c;
                                    tcol = j-c;
                                    break;
                                }
                            }

                            if(i+c<rows &&  j-c>=0){
                                if(cells[i+c][j-c].getLetter().equals(splitWord[c])){
                                    countArr[6] +=1;
                                }

                                if( countArr[6]==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    Log.d("ccaa",(i+c)+"--------"+(j-c));
                                    frow= i;
                                    fcol = j;

                                    trow= i+c;
                                    tcol = j-c;
                                    break;
                                }
                                //Log.d("cc",cells[i+c][j-c].getLetter());
                            }

                            if(i-c>=0 && j+c<columns){
                                if(cells[i-c][j+c].getLetter().equals(splitWord[c])){
                                    countArr[7] +=1;
                                }
                                if( countArr[7]==splitWord.length){
                                    Log.d("ccaa",i+"--------"+(j));
                                    Log.d("ccaa",(i-c)+"--------"+(j+c));
                                    frow= i;
                                    fcol = j;

                                    trow= i-c;
                                    tcol = j+c;
                                    break;
                                }
                                //  Log.d("cc",cells[i-c][j+c].getLetter());
                            }
                            c++;
                        }

                    }
                    if(frow!=-1 && trow!=-1 && fcol!=-1 && tcol !=-1){
                        break;
                    }
                }
                if(frow!=-1 && trow!=-1 && fcol!=-1 && tcol !=-1){
                    break;
                }
            }

          /* Log.d("ssss", String.valueOf(frow));
           Log.d("ssss", String.valueOf(fcol));
           Log.d("ssss", String.valueOf(trow));
           Log.d("ssss", String.valueOf(tcol));*/
        }


        if(frow != -1 && fcol !=-1 && trow != -1 && tcol !=-1){
            Word word = new Word(answer,true, frow,fcol,trow,tcol);
            words.add(word);

            for (int i = frow; i <=trow ; i++) {
                for (int j = fcol; j <=tcol ; j++) {
                    cells[i][j].setAnswered(true);
                    cells[i][j].setHinted(true);
                }
            }
            if(onWordSearchedListener != null){
                onWordSearchedListener.wordFound(answer);
            }
            if(onWordSearchedListener != null && isAllDone()) {
                onWordSearchedListener.onAllWordFound();
            }
            invalidate();
        }
    }

   /* public void setLetters(char[][] letters) {
        this.letters = letters;
        rows = letters.length;
        columns = letters[0].length;
        initCells();
        invalidate();
    }*/

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        textPaint.setTypeface(typeface);
        invalidate();
    }

    private boolean isFromToValid(Cell cellDragFrom, Cell cellDragTo) {
        return (Math.abs(cellDragFrom.getRow() - cellDragTo.getRow()) == Math.abs(cellDragFrom.getColumn() - cellDragTo.getColumn()))
                || cellDragFrom.getRow() == cellDragTo.getRow() || cellDragFrom.getColumn() == cellDragTo.getColumn();
    }

    public void setSplitetdList(@NotNull ArrayList<String> splitedList) {
        this.splitedList = splitedList;
    }

    private class Cell {
        private Rect rect;
        private String letter;
        private int rowIndex, columnIndex;

        public boolean isAnswered() {
            return isAnswered;
        }

        public void setAnswered(boolean answered) {
            isAnswered = answered;
        }

        private boolean isAnswered;

        private boolean isHinted;
        private int isInWord;
        public Cell(Rect rect, String letter, int rowIndex, int columnIndex, boolean isHinted, int isInWord) {
            this.rect = rect;
            this.letter = letter;
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
            this.isHinted = isHinted;
            this.isInWord = isInWord;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public int getRow() {
            return rowIndex;
        }

        public void setRow(int row) {
            this.rowIndex = row;
        }

        public int getColumn() {
            return columnIndex;
        }

        public void setColumn(int column) {
            this.columnIndex = column;
        }

        public boolean isHinted() {
            return isHinted;
        }

        public void setHinted(boolean hinted) {
            isHinted = hinted;
        }
    }

    public interface OnWordSearchedListener {
        void wordFound(String word);
        void onAllWordFound();
        void wordNotFound();
    }

    public void setOnWordSearchedListener(OnWordSearchedListener onWordSearchedListener) {
        this.onWordSearchedListener = onWordSearchedListener;
    }

   /* public void setWords(Word... words) {
        this.words = words;
    }*/

    public void setWordsList(List<String> wordsList) {
        this.wordsList = wordsList;
        if(wordsList != null && wordsList.size()>0){
            answerStatus = new int[wordsList.size()];
        }
    }


    private String getWordStr(Cell from, Cell to) {
        String word = "";
        try{
            if(Integer.valueOf(from.getRow()) != null){
                if(from.getRow() == to.getRow()) {
                    /*int c = from.getColumn() < to.getColumn() ? from.getColumn() : to.getColumn();
                    for(int i = 0; i < Math.abs(from.getColumn() - to.getColumn()) + 1; i++) {
                        word += String.valueOf(cells[from.getRow()][i+c].getLetter());
                    }*/
                    if(from.getColumn() < to.getColumn()){
                        int c = from.getColumn() < to.getColumn() ? from.getColumn() : to.getColumn();
                        for(int i = 0; i < Math.abs(from.getColumn() - to.getColumn()) + 1; i++) {
                            word += String.valueOf(cells[from.getRow()][i+c].getLetter());
                            cells[from.getRow()][i+c].setAnswered(true);
                            cells[from.getRow()][i+c].setHinted(true);
                        }
                    }else {
                        // int c = from.getColumn() < to.getColumn() ? from.getColumn() : to.getColumn();
                        for(int i = from.getColumn(); i > to.getColumn()-1; i--) {
                            word += String.valueOf(cells[from.getRow()][i].getLetter());
                            cells[from.getRow()][i].setAnswered(true);
                            cells[from.getRow()][i].setHinted(true);
                        }
                    }


                } else if(from.getColumn() == to.getColumn()) {
                    if(from.getRow()<to.getRow()){
                        int r = from.getRow() < to.getRow() ? from.getRow() : to.getRow();
                        for(int i = 0; i < Math.abs(from.getRow() - to.getRow()) + 1; i++) {
                            word += String.valueOf(cells[i+r][to.getColumn()].getLetter());
                            /*cells[i+r][to.getColumn()].setAnswered(true);
                            cells[i+r][to.getColumn()].setHinted(true);*/
                            cells[i][to.getColumn()].setAnswered(true);
                            cells[i][to.getColumn()].setHinted(true);
                        }
                    }else {
                       // int r = from.getRow() > to.getRow() ? from.getRow(): to.getRow();
                        for(int i = from.getRow(); i > to.getRow()-1; i--) {
                            cells[i][to.getColumn()].setAnswered(true);
                            cells[i][to.getColumn()].setHinted(true);
                            word += String.valueOf(cells[i][from.getColumn()].getLetter());
                        }
                       /* for(int i = from.getColumn(); i > to.getColumn()-1; i--) {
                            cells[from.getRow()][i].setAnswered(true);
                            cells[from.getRow()][i].setHinted(true);
                        }*/
                    }

                }else if(from.getRow() > to.getRow() && from.getColumn() > to.getColumn()){
                    //diagonal top to bottom
                    int fRow= from.getRow();
                    int fCol = from.getColumn();

                    while (fRow > to.getRow()-1 && fCol > to.getColumn()-1){

                        word += String.valueOf(cells[fRow][fCol].getLetter());
                        cells[fRow][fCol].setAnswered(true);
                        cells[fRow][fCol].setHinted(true);
                        fRow--;
                        fCol--;
                        Log.e("Checking_word",word);
                    }
                   /* for(int i=from.getRow(); i>to.getRow()-1; i--){
                        for(int j = from.getColumn(); j > to.getColumn()-1; j--){
                            word += String.valueOf(cells[i][j].getLetter());
                            Log.e("Checking_word",word);
                        }
                    }*/

                }else if(from.getRow() > to.getRow() && from.getColumn() < to.getColumn()){
                    int fRow= from.getRow();
                    int fCol = from.getColumn();

                    while (fRow > to.getRow()-1 && fCol < to.getColumn()+1){

                        word += String.valueOf(cells[fRow][fCol].getLetter());
                        cells[fRow][fCol].setAnswered(true);
                        cells[fRow][fCol].setHinted(true);
                        fRow--;
                        fCol++;
                        Log.e("Checking_word",word);
                    }
                }
                else {
                    if(from.getRow() > to.getRow()) {
                        Cell cell = from;
                        from = to;
                        to = cell;
                    }
                    for(int i = 0; i < Math.abs(from.getRow() - to.getRow()) + 1; i++) {
                        int foo = from.getColumn() < to.getColumn() ? i : -i;
                        word += String.valueOf(cells[from.getRow()+i][from.getColumn()+foo].getLetter());
                        cells[from.getRow()+i][from.getColumn()+foo].setAnswered(true);
                        cells[from.getRow()+i][from.getColumn()+foo].setHinted(true);

                    }
                }
            }
        }catch (Exception e){

        }
        return word;
    }

    private void highlightIfContain(String str) {
        int count = -1;
        for(String word : wordsList) {
            count++;
            if(word.equals(str)) {
                if(onWordSearchedListener != null) {
                    onWordSearchedListener.wordFound(str);
                }
                if(onWordSearchedListener != null && isAllDone()) {
                    onWordSearchedListener.onAllWordFound();
                }
                answerStatus[count] = 1;
                // word.setHighlighted(true);
                wordsSearched++;
                break;
            }
        }
    }

    public boolean isAllDone(){
        return words.size() == wordsList.size();
    }

    public void setHint(){
        if(shabdjaalBoradItem != null){
            // draw letters
            boolean isBreaked = false;
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    boolean isInWord = cells[i][j].isInWord == 1;
                    boolean isHinted =  cells[i][j].isHinted;
                    boolean isAnswered =  cells[i][j].isAnswered;


                    if(isInWord && !isHinted && !isAnswered){
                        cells[i][j].setHinted(true);
                        isBreaked = true;
                        break;
                    }
                }

                if(isBreaked){
                    invalidate();
                    break;
                }
            }
        }
    }


    public void revelWord(){
        forRevealWord();
    }

    public Set<Word> getAnsweredWordList(){
        return words;
    }

    public void setAnsweredList(Set<Word> words){
        if(this.words != null){
            this.words.clear();
        }

        this.words.addAll(words);

        Iterator<Word> it= words.iterator();
        while (it.hasNext()){
            Word data = it.next();
            for (int i = data.getFromRow(); i <=data.getToRow() ; i++) {
                for (int j = data.getFromColumn(); j <=data.getToColumn() ; j++) {
                    cells[i][j].setAnswered(true);
                    cells[i][j].setHinted(true);
                }
            }

            if(wordsList != null && !words.isEmpty() && wordsList.indexOf(data.getWord()) != -1){
                answerStatus[wordsList.indexOf(data.getWord())] = 1;
            }
        }

        invalidate();

    }

    public int getUnAnswerdWord(){
        int index = -1;
        for (int i = 0; i < answerStatus.length; i++) {
            if(answerStatus[i] != 1){
                index = i;
                answerStatus[i] = 1;
                break;
            }

        }
        return index;
    }
}
