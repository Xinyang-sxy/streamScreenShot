package org.streamScreenShot;

import java.io.IOException;
import java.nio.file.Paths;

public class StreamScreenshotUtil {

    /**
     * Capture a screenshot from a live stream.
     *
     * @param ffmpegPath the path to the FFmpeg executable
     * @param streamUrl  the URL of the live stream (e.g., rtsp://, webrtc://, flv://)
     * @param outputPath the path where the screenshot will be saved
     * @throws IOException if an error occurs while capturing the screenshot
     */
    public static void captureScreenshot(String ffmpegPath, String streamUrl, String outputPath) throws IOException {
        // Command to capture a screenshot using FFmpeg
        String command = String.format("%s -i %s -vframes 1 -q:v 2 %s",
                ffmpegPath, streamUrl, outputPath);

        // Execute the command
        Process process = Runtime.getRuntime().exec(command);

        try {
            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to capture screenshot, FFmpeg exited with code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Screenshot capture process was interrupted", e);
        }
    }

    public static void main(String[] args) {
        // Example usage
        String ffmpegPath = "E:\\download\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg";  // Specify the path to the FFmpeg executable
        String streamUrl = "https://sdk-release.qnsdk.com/flv.flv";
        String outputPath = Paths.get("screenshot.jpg").toAbsolutePath().toString();

        try {
            captureScreenshot(ffmpegPath, streamUrl, outputPath);
            System.out.println("Screenshot saved to " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

