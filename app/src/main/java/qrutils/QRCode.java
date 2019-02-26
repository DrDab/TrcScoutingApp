package qrutils;

/*
 * Copyright (c) 2018 Titan Robotics Club (http://www.titanrobotics.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;

/**
 * This class implements a simple QR Code encoder/decoder providing easy to use methods to encode messages into
 * QR code or decode QR code back to messages.
 *
 */
public class QRCode
{
    /**
     * This method encodes a message into QR code and returns the resulting image.
     * Note: if the message size is greater than the QR code capacity, it will throw a RuntimeException.
     *
     * @param msg specifies the message to be encoded.
     * @param width specifies the resulting image width in pixels.
     * @param height specifies the resulting image height in pixels.
     * @return QR code image.
     */
    public static Bitmap encodeMessage(String msg, int width, int height)
    {
        BitMatrix result;
        try
        {
            result = new MultiFormatWriter().encode(msg,
                    BarcodeFormat.QR_CODE, width, height, null);
        }
        catch (IllegalArgumentException iae)
        {
            // Unsupported format
            iae.printStackTrace();
            return null;
        }
        catch (WriterException e)
        {
            e.printStackTrace();
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++)
        {
            int offset = y * w;
            for (int x = 0; x < w; x++)
            {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }   //encodeMessage

    /**
     * This method decodes a QR code image and returns the resulting message.
     * Note: if there is no QR code in the image, it will throw a RuntimeException.
     *
     * @param image specifies the QR code image to be decoded.
     * @return decoded message.
     */
    public static String decodeMessage(Bitmap image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        Result result = null;
        try
        {
            result = reader.decode(binaryBitmap);
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
        }
        catch (ChecksumException e)
        {
            e.printStackTrace();
        }
        catch (FormatException e)
        {
            e.printStackTrace();
        }

        return result.getText();
    }   //decodeMessage

    /**
     * This method encodes a message into QR code and writes the resulting image to the specified file.
     *
     * @param msg specifies the message to be encoded.
     * @param width specifies the resulting image width in pixels.
     * @param height specifies the resulting image height in pixels.
     * @param filePath specifies the file path for writing the QR code image.
     * @throws IOException if the operation failed (e.g. invalid file path).
     */
    public static void writeMessage(String msg, int width, int height, String filePath) throws IOException
    {
        Bitmap bmp = encodeMessage(msg, width, height);
        try
        {
            FileOutputStream out = new FileOutputStream(filePath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }   //writeMessage

    /**
     * This method decodes a QR code image file and returns the resulting message.
     *
     * @param filePath specifies the QR code image file.
     * @return decoded message.
     * @throws IOException if the operation failed (e.g. invalid file path).
     */
    public static String readMessage(String filePath) throws IOException
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return decodeMessage(bitmap);
    }   //readMessage

}   //class QRCode