import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by inksmallfrog on 10/2/15.
 */
public class TableGui extends JFrame{
    private java.util.List<ClassInfo> classInfos;
    private String[] headers = {
            "课头号",
            "课程名称",
            "课程类型",
            "学分",
            "教师",
            "授课学院",
            "学习类型",
            "学年",
            "学期",
            "成绩"
    } ;

    JPanel mainPanel;
    JTable table;
    JScrollPane scrollPane;
    JButton exportButton;

    TableGui(){
        setSize(1024, 768);

        mainPanel = new JPanel();
        mainPanel.setSize(getWidth(), getHeight());
        mainPanel.setLayout(null);

        InitTable();

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, mainPanel.getWidth(), mainPanel.getHeight() - 80);
        mainPanel.add(scrollPane);

        exportButton = new JButton("导出");
        exportButton.setBounds(mainPanel.getWidth() - 150, mainPanel.getHeight() - 70, 100, 25);
        mainPanel.add(exportButton);
        exportButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                XLSOperator xls = new XLSOperator();
                xls.XLSExport(table);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        add(mainPanel);
    }

    public void InitTable(){
        ScoreReader test = new ScoreReader(new File("scoreChart.html"));
        classInfos = test.getClassInfos();

        table = new JTable(classInfos.size() + 3, 10);

        for(int i = 0; i < headers.length; ++i){
            table.setValueAt(headers[i], 0, i);
        }

        table.setDefaultRenderer(Object.class, new TableRenderer());

        int i = 1;
        for(ClassInfo classInfo : classInfos){
            table.setValueAt(classInfo.getClassId(), i, 0);
            table.setValueAt(classInfo.getClassName(), i, 1);
            table.setValueAt(classInfo.getClassType(), i, 2);
            table.setValueAt(classInfo.getClassCredit(), i, 3);
            table.setValueAt(classInfo.getTeacherName(), i, 4);
            table.setValueAt(classInfo.getClassSchool(), i, 5);
            table.setValueAt(classInfo.getStudyType(), i, 6);
            table.setValueAt(classInfo.getSchoolYear(), i, 7);
            table.setValueAt(classInfo.getTerm(), i, 8);

            float score = classInfo.getScore();
            if(score >= 0.0){
                table.setValueAt(classInfo.getScore(), i, 9);
            }

            ++i;
        }
        table.setValueAt("总计：", i, 0);
        table.setValueAt("加权平均分:", i + 1, 0);
        table.setValueAt(test.getAverageScore(), i + 1, 1);
        table.setValueAt("加权gpa:", i + 1, 3);
        table.setValueAt(test.getGpa(), i + 1, 4);
    }

    public void Reset(){
        mainPanel.setSize(getWidth(), getHeight());
        scrollPane.setBounds(0, 0, mainPanel.getWidth(), mainPanel.getHeight() - 80);
        exportButton.setBounds(mainPanel.getWidth() - 150, mainPanel.getHeight() - 70, 100, 25);
    }
}

class TableRenderer implements TableCellRenderer {
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(row < table.getRowCount() - 2){
            if(row != 0 && column == 0){
                Object score = table.getValueAt(row, 9);
                if(score == null){
                    renderer.setForeground(Color.darkGray);
                }
                else if(Float.parseFloat(score.toString()) < 60.0f){
                    renderer.setForeground(Color.red);
                }
                else{
                    renderer.setForeground(Color.black);
                }
            }
        }
        else if(row == table.getRowCount() - 2){
            renderer.setForeground(Color.black);
        }

        return renderer;
    }
}