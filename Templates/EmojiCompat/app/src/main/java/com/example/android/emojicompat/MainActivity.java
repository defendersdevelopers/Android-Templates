/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.emojicompat;

import android.content.Context;
import android.os.Bundle;
import android.support.text.emoji.EmojiCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import static com.example.android.emojicompat.EmojiCompatApplication.USE_BUNDLED_EMOJI;

public class MainActivity extends AppCompatActivity {

    // [U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F4BB] (PERSONAL COMPUTER)
    private static final String WOMAN_TECHNOLOGIST = "\uD83D\uDC69\u200D\uD83D\uDCBB";

    // [U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F3A4] (MICROPHONE)
    private static final String WOMAN_SINGER = "\uD83D\uDC69\u200D\uD83C\uDFA4";

    static final String EMOJI = WOMAN_TECHNOLOGIST + " " + WOMAN_SINGER;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (USE_BUNDLED_EMOJI != 2) {
            setContentView(R.layout.activity_main_emoji);

            // Regular TextView without EmojiCompat support; you have to manually process the text
            final TextView regularTextView = findViewById(R.id.regular_text_view);
            regularTextView.setText(getString(R.string.emoji_edit_text, EMOJI));
            EmojiCompat.get().registerInitCallback(new InitCallback(regularTextView));

            // TextView variant provided by EmojiCompat library
            final TextView emojiTextView = findViewById(R.id.emoji_text_view);
            emojiTextView.setText(getString(R.string.emoji_text_view, EMOJI));

            // Custom TextView
            final TextView customTextView = findViewById(R.id.emoji_custom_text_view);
            customTextView.setText(getString(R.string.custom_text_view, EMOJI));

            // EditText variant provided by EmojiCompat library
            final TextView emojiEditText = findViewById(R.id.emoji_edit_text);
            emojiEditText.setText(getString(R.string.emoji_edit_text, EMOJI));

            // Button variant provided by EmojiCompat library
            final TextView emojiButton = findViewById(R.id.emoji_button);
            emojiButton.setText(getString(R.string.emoji_edit_text, EMOJI));
        } else {
            setContentView(R.layout.activity_main_regular);

            // Regular TextView without EmojiCompat support; you have to manually process the text
            final TextView regularTextView = findViewById(R.id.regular_text_view);
            regularTextView.setText(getString(R.string.regular_text_view, EMOJI));

            // Regular edit text
            final TextView regularEditText = findViewById(R.id.regular_edit_text);
            regularEditText.setText(getString(R.string.regular_edit_text, EMOJI));
        }
    }

    private static class InitCallback extends EmojiCompat.InitCallback {

        private final WeakReference<TextView> mRegularTextViewRef;

        InitCallback(TextView regularTextView) {
            mRegularTextViewRef = new WeakReference<>(regularTextView);
        }

        @Override
        public void onInitialized() {
            final TextView regularTextView = mRegularTextViewRef.get();
            if (regularTextView != null) {
                final EmojiCompat compat = EmojiCompat.get();
                final Context context = regularTextView.getContext();
                regularTextView.setText(
                        compat.process(context.getString(R.string.regular_text_view, EMOJI)));
            }
        }

    }
}
