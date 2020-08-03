package life;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class GameOfLife extends JFrame {
	JLabel generationLabel;
	JLabel aliveLabel;
	JPanel infoPanel;
	JPanel cellsFieldPanel;
	JButton rerol;
	JToggleButton start;

	public void showCurrentGeneration(boolean[][] currentGeneration) {
		JPanel cellsFieldPanel = new JPanel();
		cellsFieldPanel.setLayout(new GridLayout(currentGeneration.length, currentGeneration.length, 1, 1));
		drawCellsFieldWithPanels(currentGeneration, cellsFieldPanel);
		this.cellsFieldPanel = cellsFieldPanel;
		add(cellsFieldPanel, BorderLayout.CENTER);
	}

	void drawCellsFieldWithPanels(boolean[][] currentGeneration, JPanel cellsField) {
		for (int row = 0; row < currentGeneration.length; row++) {
			for (int column = 0; column < currentGeneration[row].length; column++) {
				JPanel cell = new JPanel();

				cell.setBackground(currentGeneration[row][column] ? Color.BLACK : Color.WHITE);
				cellsField.add(cell);
			}
		}
	}

	JButton buildButton(String text, String name) {
		JButton button = new JButton();
		button.setText(text);
		button.setName(name);
		button.setBounds(20, 10 , 40, 30);
		return button;
	}

	JToggleButton buildButton1(String text, String name) {
		JToggleButton button = new JToggleButton();
		button.setText(text);
		button.setName(name);
		button.setBounds(20, 10 , 40, 30);
		return button;
	}

	JLabel buildLabel(String text, String name) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setName(name);
		label.setBounds(40, 20, 100, 30);
		return label;
	}

	JPanel buildInfoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(40,150,220,70);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		return infoPanel;
	}

	JLabel buildGenerationLabel(String generationNumber) {
		return buildLabel("Generation #" + generationNumber, "GenerationLabel");
	}

	JLabel buildAliveLabel(String aliveNumber) {
		return buildLabel("Alive : " + aliveNumber, "AliveLabel");
	}
	JToggleButton buildJButoon(String name) {
		return buildButton1("" + name, "PlayToggleButton");
	}

	void setStart(String name) {
		start.setText(name);
	}

	void setGenerationLabel(String generationNumber) {
		generationLabel.setText("Generation #" + generationNumber);
	}

	void setAliveLabel(String generationNumber) {
		aliveLabel.setText("Alive : " + generationNumber);
	}

	public GameOfLife() throws InterruptedException {


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Game of Life");
		setSize(500, 500);
		setLayout(new BorderLayout());


		generationLabel = buildGenerationLabel("1");
		aliveLabel = buildAliveLabel("0");
		start = buildJButoon("PlayToggleButton");
		rerol = buildButton("ResetButton", "ResetButton");

		boolean[][] ar = new boolean[20][20];
		infoPanel = buildInfoPanel();
		infoPanel.add(start);
		infoPanel.add(rerol);
		infoPanel.add(generationLabel);
		infoPanel.add(aliveLabel);
		add(infoPanel, BorderLayout.WEST);
		Universe universe = new Universe(20);
		universe.play();
		showCurrentGeneration(ar);
		setVisible(true);
		for (int i = 0; i < 20; i++) {
			universe.getGeneration(i);
			setGenerationLabel(universe.generation + "");
			setAliveLabel(universe.alive + "");
			start.addActionListener(e -> {
						setStart("start");
					});
			showCurrentGeneration(universe.arr);
			setVisible(true);
		}
	}
}
