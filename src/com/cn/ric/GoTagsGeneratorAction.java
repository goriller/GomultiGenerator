package com.cn.ric;

import com.cn.ric.entity.StructGenerateResult;
import com.cn.ric.util.MethodUtil;
import com.cn.ric.util.StructUtil;
import com.cn.ric.util.TagsUtil;
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

/**
 * @program: GoBuilderGeneratorPlugin
 * @description:
 * @author: richen
 * @create: 2020-08-14 15:24
 **/
public class GoTagsGeneratorAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project =  event.getData(PlatformDataKeys.PROJECT);
        final Editor editor = event.getData(PlatformDataKeys.EDITOR);
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

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        String selectedText = editor.getSelectionModel().getSelectedText();
        StructGenerateResult structGenerateResult = StructUtil.generateStruct(selectedText);
        if (!StringUtils.isBlank(structGenerateResult.error)) {
            Messages.showErrorDialog(project, structGenerateResult.error, "Generate Failed");
            return;
        }

        final String pattern = TagsUtil.generateTagsPatternCode(selectedText, true, true, false);

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
            editor.getDocument().replaceString(start,
                    end, pattern);
            editor.getCaretModel().moveToOffset(caretModel.getOffset()-2);
            editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
        });
    }
}
