using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace Island
{
    public partial class IslandForm : Form
    {
        public IslandForm()
        {
            InitializeComponent();
        }

        private void GenerateButton_Click(object sender, EventArgs e)
        {
            if (columnTextBox.Lines.Length != lengthTextBox.Lines.Length)
            {
                logTextBox.AppendText("Line count mismatch, check your input in NAME and LENGTH!\r\n");
                return;
            }
            logTextBox.AppendText("Starting...\r\n");
            workingProgressBar.Value = 0;
            workingProgressBar.Maximum = columnTextBox.Lines.Length * 4 + 20 * 4;
            List<string> anyTransDefinitions = new List<string>();
            // Input start
            anyTransDefinitions.Add("BEGIN_MESSAGE            INPUT.any_IN");
            anyTransDefinitions.Add("CHARACTER_SET            EBCDIC");
            anyTransDefinitions.Add("");
            anyTransDefinitions.Add("BEGIN_SEGMENT            rec,,M,9999,0");
            // Elements
            for (int i = 0; i < columnTextBox.Lines.Length; i++)
            {
                if (!string.IsNullOrEmpty(columnTextBox.Lines[i]))
                {
                    anyTransDefinitions.Add(" ELEMENT                 " + (i + 1).ToString() + columnTextBox.Lines[i].Trim() + ",,OI,character," + lengthTextBox.Lines[i].Trim() + ",,left");
                }
                workingProgressBar.Value++;
            }
            // Input end
            anyTransDefinitions.Add("END_SEGMENT");
            anyTransDefinitions.Add("");
            anyTransDefinitions.Add("END_MESSAGE");
            anyTransDefinitions.Add("");
            // Outpur start
            anyTransDefinitions.Add("BEGIN_MESSAGE            OUTPUT.any_OUT");
            anyTransDefinitions.Add("CHARACTER_SET            UTF8");
            anyTransDefinitions.Add("BOM                      OFF");
            anyTransDefinitions.Add("SEGMENT_TERMINATOR       0x0a");
            anyTransDefinitions.Add("ELEMENT_SEPARATOR        \\\\t");
            anyTransDefinitions.Add("");
            anyTransDefinitions.Add("BEGIN_SEGMENT            rec,,M,9999,0");
            // Elements
            int count = 0;
            for (int i = 0; i < columnTextBox.Lines.Length; i++)
            {
                if (!string.IsNullOrEmpty(columnTextBox.Lines[i]))
                {
                    if (columnTextBox.Lines[i].Contains("ｱｸｾｽNO"))
                    {
                        logTextBox.AppendText("Found special item, escaped.\r\n");
                        anyTransDefinitions.Add(" ELEMENT                 " + (i + 1).ToString() + columnTextBox.Lines[i].Trim() + ",,OI,character," + lengthTextBox.Lines[i].Trim() + ",,left,,,,,\\\"    \\\",");
                        count++;
                    }
                    else
                    {
                        anyTransDefinitions.Add(" ELEMENT                 " + (i + 1).ToString() + columnTextBox.Lines[i].Trim() + ",,OI,character," + lengthTextBox.Lines[i].Trim() + ",,left,,,,,:{rec}." + (i + 1).ToString() + columnTextBox.Lines[i].Trim() + ",");
                        count++;
                    }
                }
                workingProgressBar.Value++;
            }
            // Output end
            anyTransDefinitions.Add("END_SEGMENT");
            anyTransDefinitions.Add("");
            anyTransDefinitions.Add("END_MESSAGE");

            // Display
            anyTextBox.Text = string.Empty;
            anyTransDefinitions.ForEach(x =>
            {
                if (x != "")
                {
                    anyTextBox.AppendText(x + "\r\n");
                }
                else if (string.IsNullOrEmpty(x))
                {
                    anyTextBox.AppendText("\r\n");
                }
                workingProgressBar.Value++;
            });
            logTextBox.AppendText(count + " item(s) generated.\r\n");
            workingProgressBar.Value = workingProgressBar.Maximum;
            logTextBox.AppendText("Finished\r\n");
        }

        List<string> originalTxt = new List<string>();
        List<string> shrinkedTxt = new List<string>();
        List<int> matchedLines = new List<int>();
        private void ShrinkButton_Click(object sender, EventArgs e)
        {
            originalTxt = new List<string>();
            shrinkedTxt = new List<string>();
            workingProgressBar.Value = 0;
            workingProgressBar.Maximum = workingTextBox.Lines.Length * 2;
            logTextBox.AppendText("Shrinking...\r\n");
            logTextBox.AppendText(workingTextBox.Lines.Length + " line(s) loaded.\r\n");
            // Remove tab
            int count = 0;
            List<string> rawTxt = new List<string>(workingTextBox.Lines);
            for (int i = 0; i < rawTxt.Count; i++)
            {
                if (!string.IsNullOrEmpty(rawTxt[i]))
                {
                    originalTxt.Add(rawTxt[i]);
                    shrinkedTxt.Add(rawTxt[i].Replace("\t", ""));
                    count++;
                }
                workingProgressBar.Value++;
            }
            logTextBox.AppendText(count + " line(s) operated in total. \r\n");

            // Refresh working txt
            workingTextBox.Text = string.Empty;
            StringBuilder sb = new StringBuilder();
            shrinkedTxt.ForEach(x =>
            {
                if (x != "")
                {
                    sb.Append(x + "\r\n");
                    workingProgressBar.Value++;
                }
            });
            workingTextBox.Text = sb.ToString();
            workingProgressBar.Value = workingProgressBar.Maximum;
            logTextBox.AppendText("Shrinked.\r\n");
        }

        private void MatchButton_Click(object sender, EventArgs e)
        {
            matchedLines = new List<int>();
            workingProgressBar.Value = 0;
            workingProgressBar.Maximum = shrinkedTxt.Count * 2;
            logTextBox.AppendText("Matching...\r\n");

            string matchSample = matchTextBox.Text.Replace("\t", "");
            logTextBox.AppendText("Using keyword: " + matchSample + "\r\n");
            matchTextBox.Text = matchSample;

            // Match
            int count = 0;
            for (int i = 0; i < shrinkedTxt.Count; i++)
            {
                if (shrinkedTxt[i].Contains(matchSample))
                {
                    matchedLines.Add(i);
                    count++;
                }
                workingProgressBar.Value++;
            }

            // Display
            matchedTextBox.Text = "";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < matchedLines.Count; i++)
            {
                sb.Append(originalTxt[matchedLines[i]] + "\r\n");
                workingProgressBar.Value++;
            }
            matchedTextBox.Text = sb.ToString();
            workingProgressBar.Value = workingProgressBar.Maximum;
            logTextBox.AppendText(count + " line(s) matched in total.\r\n");
        }

        private void NextButton_Click(object sender, EventArgs e)
        {
            workingProgressBar.Value = 0;
            workingProgressBar.Maximum = originalTxt.Count;

            logTextBox.AppendText("Preparing...\r\n");
            int count = 0;
            workingTextBox.Text = "";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < originalTxt.Count; i++)
            {
                if (!matchedLines.Contains(i))
                {
                    sb.Append(originalTxt[i] + "\r\n");
                    count++;
                }
                workingProgressBar.Value++;
            }
            workingTextBox.Text = sb.ToString();
            workingProgressBar.Value = workingProgressBar.Maximum;
            logTextBox.AppendText(count + " line(s) prepraed in total.\r\n");
        }

        private void SplitButton_Click(object sender, EventArgs e)
        {
            logTextBox.AppendText("Splitting...\r\n");
            workingProgressBar.Value = 0;
            workingProgressBar.Maximum = matchedLines.Count;

            matchedTextBox.Text = "";
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < matchedLines.Count; i++)
            {
                List<string> matchedOldSplitLine = new List<string>(originalTxt[matchedLines[i]].Split('\t'));
                string splittedLine = "";
                int startIndex = 0;
                string restoredLine = "";
                for (int j = 0; j < matchedOldSplitLine.Count; j++)
                {
                    if (matchedOldSplitLine[j].Length >= int.Parse(oldIndexTextBox.Lines[j]))
                    {
                        restoredLine += matchedOldSplitLine[j];
                    }
                    else
                    {
                        restoredLine += matchedOldSplitLine[j].PadRight(int.Parse(oldIndexTextBox.Lines[j]));
                    }
                }

                for (int j = 0; j < splitIndexTextBox.Lines.Length; j++)
                {
                    if (!string.IsNullOrEmpty(splitIndexTextBox.Lines[j]))
                    {
                        string splitted = restoredLine.Substring(startIndex, int.Parse(splitIndexTextBox.Lines[j]));
                        if (string.IsNullOrWhiteSpace(splitted))
                        {
                            splitted = "";
                        }
                        splittedLine += splitted.Trim();
                        splittedLine += "\t";
                        startIndex += int.Parse(splitIndexTextBox.Lines[j]);
                    }
                }
                splittedLine = splittedLine.Remove(splittedLine.LastIndexOf("\t"), 1);
                sb.Append(splittedLine + "\r\n");

                workingProgressBar.Value++;
                count++;
            }
            matchedTextBox.Text = sb.ToString();
            workingProgressBar.Value = workingProgressBar.Maximum;
            logTextBox.AppendText(count + " line(s) splitted in total.\r\n");
        }
    }
}
