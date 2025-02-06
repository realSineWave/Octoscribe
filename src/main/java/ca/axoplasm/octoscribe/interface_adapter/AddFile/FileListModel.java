package ca.axoplasm.octoscribe.interface_adapter.AddFile;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FileListModel extends AbstractTableModel {
    List<FileState> fileStates;

    public FileListModel(List<FileState> fileStates) {
        this.fileStates = fileStates;
    }

    @Override
    public int getRowCount() {
        return fileStates.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FileState fileState = fileStates.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> fileState.getFileName();
            case 1 -> fileState.getStatus().toString();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "File Name";
            case 1 -> "Status";
            default -> null;
        };
    }
}
