/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rpcproject.app;

import rpcproject.list.LinkedList;

import static rpcproject.utilities.StringUtils.join;
import static rpcproject.utilities.StringUtils.split;
import static rpcproject.app.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
    }
}
