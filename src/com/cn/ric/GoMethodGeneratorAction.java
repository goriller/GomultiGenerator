package com.cn.ric;

import com.cn.ric.entity.StructGenerateResult;
import com.cn.ric.util.MethodUtil;
import com.cn.ric.util.StructUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @program: GoBuilderGeneratorPlugin
 * @description:
 * @author: richen
 * @create: 2020-08-17 14:34
 **/

public class GoMethodGeneratorAction  extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project =  e.getData(PlatformDataKeys.PROJECT);
        final Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (null == editor) {
            return;
        }

        Document document = editor.getDocument();
        String extension = Objects.requireNonNull(FileDocumentManager.getInstance().getFile(document)).getExtension();
        if (!(extension != null && extension.toLowerCase().equals("go"))){
            return;
        }
        if (!document.isWritable()) {
            return;
        }

        final CaretModel caretModel = editor.getCaretModel();
        SelectionModel selectionModel = editor.getSelectionModel();

        int selectedLineNumber = document.getLineNumber(selectionModel.getSelectionEnd());

        final TextRange lineRange = new TextRange(
                document.getLineStartOffset(selectedLineNumber),
                document.getLineEndOffset(selectedLineNumber)
        );

        String selectedLine = document.getText().substring(lineRange.getStartOffset(), lineRange.getEndOffset()).trim();
        if (StringUtils.isBlank(selectedLine)) {
            Messages.showErrorDialog(project, "please move the cursor to the correct position", "Generate Failed");
            return;
        }
        String text = document.getText();

        final String pattern = MethodUtil.generateMethodPatternCode(selectedLine, text);
        if (StringUtils.isBlank(pattern)) {
            Messages.showErrorDialog(project, "please move the cursor to the correct position", "Generate Failed");
            return;
        }

//        new WriteCommandAction(project) {
//            @Override
//            protected void run(@NotNull Result result) {
//                editor.getDocument().replaceString(lineRange.getStartOffset(),
//                        lineRange.getEndOffset(), pattern);
//                editor.getCaretModel().moveToOffset(caretModel.getOffset()-2);
//                editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
//            }
//        }.execute();

        WriteCommandAction.runWriteCommandAction(project, () ->  {
            editor.getDocument().replaceString(lineRange.getStartOffset(),
                    lineRange.getEndOffset(), pattern);
            editor.getCaretModel().moveToOffset(caretModel.getOffset()-2);
            editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
        });
    }
}
