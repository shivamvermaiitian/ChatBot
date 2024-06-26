package com.example.easybot

import android.graphics.Paint.Align
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.node.CanFocusChecker.end
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easybot.ui.theme.ModelMessage
import com.example.easybot.ui.theme.Purple80
import com.example.easybot.ui.theme.UserMessage

@Composable
fun ChatScreen(modifier: Modifier=Modifier,viewModel: ChatViewModel){
//    Text(text = "chat")
    Column(
        modifier=Modifier,
    ) {
        HeaderApp()
        MessageList(modifier=Modifier.weight(1f),messageList =viewModel.messageList)
        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}
////

@Composable
fun MessageList(modifier: Modifier=Modifier,messageList: List<MessageModel>) {
    if (messageList.isEmpty()){
        Column(
            modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Icon(modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = Purple80)
            Text(text = "Ask me Anything", fontSize = 22.sp)
        }
    }else
    {
        LazyColumn(
            modifier=modifier,
            reverseLayout = true,
        ) {
            items(messageList.reversed()){
//            Text(text = it.message)
                MessageRow(messageModel = it)
            }
        }
    }

}
//

@Composable
fun MessageRow(messageModel: MessageModel){
    val isModel=messageModel.role=="model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(

                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) ModelMessage else UserMessage)
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(text =messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White )
                }
            }
        }

    }

}

@Composable
fun  MessageInput(onMessageSend:(String)->Unit){
    var message by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
//            Text(text = "Enter your question"),
//            TextFieldValue("Enter"),
            modifier = Modifier.weight(1f),
            value = message, onValueChange = { message=it})
        IconButton(onClick = {
            if (message.isNotEmpty()){
                onMessageSend(message)
                message=""
            }
        }) {
            Icon(imageVector =Icons.Default.Send  , contentDescription = "Send")
        }

    }
}

//

@Composable
fun HeaderApp(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
        ){
        Text(modifier =Modifier.padding(24.dp), text = "ChatBot", color = Color.Blue, fontSize = 20.sp)
    }
}
//
//
//hello