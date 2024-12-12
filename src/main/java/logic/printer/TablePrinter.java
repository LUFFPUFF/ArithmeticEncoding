package logic.printer;

public class TablePrinter {

    public static String printTable(String[] headers, String[][] rows) {

        StringBuilder builder = new StringBuilder();

        int[] columnWidths = calculateColumnWidths(headers, rows);

        builder.append(printRow(headers, columnWidths)).append("\n");

        builder.append(printSeparator(columnWidths)).append("\n");

        for (String[] row : rows) {
            builder.append(printRow(row, columnWidths)).append("\n");
        }

        builder.append(printSeparator(columnWidths));

        return builder.toString();
    }

    private static int[] calculateColumnWidths(String[] headers, String[][] rows) {
        int columnCount = headers.length;
        int[] widths = new int[columnCount];

        for (int i = 0; i < columnCount; i++) {
            widths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < columnCount; i++) {
                if (row[i] != null && row[i].length() > widths[i]) {
                    widths[i] = row[i].length();
                }
            }
        }

        return widths;
    }

    private static String printRow(String[] row, int[] columnWidths) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < row.length; i++) {
            String cell = row[i] != null ? row[i] : "";
            sb.append(String.format(" %-"+columnWidths[i]+"s |", cell));
        }
        return sb.toString();
    }

    private static String printSeparator(int[] columnWidths) {
        StringBuilder sb = new StringBuilder("+");
        for (int width : columnWidths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        return sb.toString();
    }
}
