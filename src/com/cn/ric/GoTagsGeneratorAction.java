package com.cn.ric;

import com.cn.ric.util.MethodUtil;
import com.cn.ric.util.TagsUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

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
        Document document = editor.getDocument();
        if (null == editor) {
            return;
        }

        final CaretModel caretModel = editor.getCaretModel();
        SelectionModel selectionModel = editor.getSelectionModel();

        final TextRange lineRange = new TextRange(
                selectionModel.getSelectionStart(),
                selectionModel.getSelectionEnd()
        );

        String originText = document.getText().trim();
        final String pattern = TagsUtil.generateTagsPatternCode(originText, true, true, false);

        new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) {
                editor.getDocument().replaceString(lineRange.getStartOffset(),
                        lineRange.getEndOffset(), pattern);
                editor.getCaretModel().moveToOffset(caretModel.getOffset()-2);
                editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
            }
        }.execute();
    }
}
