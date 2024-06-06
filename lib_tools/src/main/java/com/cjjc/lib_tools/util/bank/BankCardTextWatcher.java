package com.cjjc.lib_tools.util.bank;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.cjjc.lib_tools.listener.IBank;
import com.cjjc.lib_tools.util.log.LogUtil;

/**
 * 银行卡格式输入框
 */
public class BankCardTextWatcher implements TextWatcher {

    //default max length = 21 + 5 space
    private static final int DEFAULT_MAX_LENGTH = 21 + 5;
    //max input length
    private int maxLength = DEFAULT_MAX_LENGTH;
    private int beforeTextLength = 0;
    private boolean isChanged = false;

    //space count
    private int space = 0;

    private StringBuffer buffer = new StringBuffer();
    private EditText editText;
    private static TextView showBankType;
    private static TextView showBankName;
    private String bankType = "";
    private IBank iBank;//回调

    /**
     *
     * @param editText  需要绑定的EditText
     * @param showBankName  展示银行卡名称的控件
     * @param showBankType  展示银行卡类型的控件
     * EventBus :checkBankError 当前卡号不支持      checkBankSuccess:查询成功
     */
    public static void bind(EditText editText, TextView showBankName, TextView showBankType) {
        BankCardTextWatcher.showBankName = showBankName;
        BankCardTextWatcher.showBankType = showBankType;
        new BankCardTextWatcher(editText, DEFAULT_MAX_LENGTH);
    }

    public static void bind(EditText editText, int maxLength) {
        new BankCardTextWatcher(editText, maxLength);
    }

    public BankCardTextWatcher(EditText editText, int maxLength) {
        this.editText = editText;
        this.maxLength = maxLength;
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeTextLength = s.length();
        if (buffer.length() > 0) {
            buffer.delete(0, buffer.length());
        }
        space = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                space++;
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int length = s.length();
        buffer.append(s.toString());
        if (length == beforeTextLength || length <= 3
                || isChanged) {
            isChanged = false;
            return;
        }
        isChanged = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isChanged) {
            bankType = editText.getText().toString();
            if (editText.getText().toString().length() > 14) {
                getBankType();
            }else {
                showBankName.setText(null);
                showBankType.setText(null);
            }
            int selectionIndex = editText.getSelectionEnd();
            //total char length
            int index = 0;
            while (index < buffer.length()) {
                if (buffer.charAt(index) == ' ') {
                    buffer.deleteCharAt(index);
                } else {
                    index++;
                }
            }
            //total space count
            index = 0;
            int totalSpace = 0;
            while (index < buffer.length()) {
                if ((index == 4 || index == 9 || index == 14 || index == 19 || index == 24)) {
                    buffer.insert(index, ' ');
                    totalSpace++;
                }
                index++;
            }
            //selection index
            if (totalSpace > space) {
                selectionIndex += (totalSpace - space);
            }
            char[] tempChar = new char[buffer.length()];
            buffer.getChars(0, buffer.length(), tempChar, 0);
            String str = buffer.toString();
            if (selectionIndex > str.length()) {
                selectionIndex = str.length();
            } else if (selectionIndex < 0) {
                selectionIndex = 0;
            }
            editText.setText(str);
            Editable text = editText.getText();
            //set selection
            Selection.setSelection(text, selectionIndex < maxLength ? selectionIndex : maxLength);
            isChanged = false;
        }
    }

    public void getBankType() {
        showBankName.setHint("");
        if (bankType != null && !bankType.equals("")) {
            String bankNumber = bankType.replace(" ", "");
            LogUtil.xLoge("当前卡号==>" + bankNumber);
            if (BankCheck.checkBankCard(bankNumber)) {
                String detailNameOfBank = BankCheck.getDetailNameOfBank(bankNumber);
                if (detailNameOfBank != null && !detailNameOfBank.equals("")) {
                    if (detailNameOfBank.contains("·")) {
                        String[] split = detailNameOfBank.split("·");
                        if (split.length == 2) {
                            callBack(true);
                            if (split[1].equals("--")) {
                                showBankType.setText("储蓄卡");
                            } else {
                                showBankType.setText(split[1]);
                            }
                            showBankName.setText(split[0]);
                        }
                    }
                } else {
                    String nameOfBank = BankCheck.getNameOfBank(bankNumber);
                    if (nameOfBank != null && !nameOfBank.equals("")) {
                        if (nameOfBank.contains("·")) {
                            String[] split = nameOfBank.split("·");
                            if (split.length == 2) {
                                callBack(true);
                                if (split[1].equals("--")) {
                                    showBankType.setText("储蓄卡");
                                } else {
                                    showBankType.setText(split[1]);
                                }
                                showBankName.setText(split[0]);
                            }
                        }
                    }else {
                        callBack(false);
                        showBankName.setText(null);
                        showBankType.setText(null);
                    }
                }
            }else {
                callBack(false);
                showBankName.setText(null);
                showBankType.setText(null);
            }
        }else {
            callBack(false);
            showBankName.setText(null);
            showBankType.setText(null);
        }
    }

    public void callBack(boolean type){
        if(iBank!=null){
            iBank.onResult(type);
        }
    }

    public void setIBank(IBank iBank) {
        this.iBank = iBank;
    }
}
