package gallium.command;

import gallium.main.GalliumException;
import gallium.main.Storage;
import gallium.main.TaskList;
import gallium.main.Ui;

import gallium.task.Task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkCommand extends Command {
    private String message;
    private static final String MARK = "mark";
    private static final String UNMARK = "unmark";

    public MarkCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GalliumException {
        try {
            boolean isMark = message.startsWith(MARK);
            Pattern pattern = Pattern.compile((isMark ? MARK : UNMARK) + " (\\d+)");
            Matcher matcher = pattern.matcher(message);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));
                Task task = taskList.getTask(index - 1);
                task.setIsDone(isMark);
                ui.printMarkMessage(isMark, task);
            }
        } catch (IndexOutOfBoundsException e) {
            ui.showWrongIndex();
        }
    }
}