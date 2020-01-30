	package v6;


import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.corba.se.spi.activation.Server;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
public class clientController implements Initializable {
	
	@FXML private JFXButton sendMessageButton;		
	@FXML private JFXButton sendFileButton;
	@FXML public  JFXTextField sendTextField;
	@FXML private JFXListView<Account> accountListview = new JFXListView<>();		//���ߵ��û��б�
	@FXML private Label otherAccountLabel;
	@FXML private JFXTextArea reciveTextArea = new JFXTextArea();
	@FXML private JFXButton refreshAccountsListButton;
	static public List<Account> accountsList;				//��ǰ�����û��б�
	static public ObservableList<Account> acc;
	static private String otherAccountName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Account> acc = FXCollections.observableArrayList(accountsList);
		accountListview.setItems(acc);
		reciveTextArea.appendText("init");
	}
	
	
	    /**
	    * @Title: clickSendButton
	    * @Description: TODO ��������͡���ť����¼�������ʱ�������ı����зǿյ��ַ������ͳ�ȥ��Ȼ����������ı��򣬲����Ϸ�����ʾ�ı����л�����Ϣ
	    * @param @param event    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public void clickSendMessageButton(ActionEvent event) {
		System.out.println("sendButton clicked");
		String yourMessage = sendTextField.getText();
		System.out.println("You said: " + yourMessage);
		if(!yourMessage.isEmpty()) {
			while(!(new communication().sendMessage(yourMessage, TalkClient.socket, TalkClient.account, TalkClient.theOtherAccount))) {
				continue;
			}
			sendTextField.clear();
			//TODO:��ʱҪ���Ϸ����ı����л����û��������Ϣ���Լ�һЩ������Ϣ����ʱ��ȵ�
			reciveTextArea.setStyle("-fx-font-size: 16");	//�����ֺ�
			reciveTextArea.setStyle("-fx-text-fill:pink");	//������ɫ
			reciveTextArea.appendText("You: \"" + TalkClient.account.getId() + "-" + TalkClient.account.getName() + "\" at Time: " + (new Date()) + " said: \n");
			reciveTextArea.appendText(yourMessage + "\n");
			
		}
		
		System.out.println("button clicked!");
		sendTextField.setText("23333333");
	}
	
	public void reciveMessage(String message) {
//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
////				reciveTextArea.appendText(message + "\n");
//				reciveTextArea.setText(message + "\n");
////				System.out.println("reciveMessage " + message);
//				
//				return;
//			}
//		});	
		if (Platform.isFxApplicationThread()) {
	        reciveTextArea.appendText(message + "\n");
	    } else {
	        Platform.runLater(() -> reciveTextArea.appendText(message + "\n"));
	    }
	}
	
	public void clickSendFileButton(ActionEvent event) {
		System.out.println("User paper to send file....");
	}
	public void clickRefreshAccountsListButton(ActionEvent event) {
//		new Thread(new Task<Void>() {
//
//	        // call����������̷߳�JavaFX�߳�
//	        @Override
//	        protected Void call() throws Exception {
//				//���accountsListView�ؼ��Ľ���
//				accountListview.getSelectionModel().clearSelection();
//				
//				System.out.println(acc.get(0).getId() + "******" + acc.get(0).getName());
////				accountListview.setItems(null);
//				accountListview.getItems().clear();
////				accountListview.setItems(acc);
////				accountListview.refresh();
//				accountListview.getItems().addAll(acc);
//	            System.out.println("call done");
//	            return null;
//	        }
//
//	        @Override
//	        protected void succeeded() {
//				//���accountsListView�ؼ��Ľ���
//				accountListview.getSelectionModel().clearSelection();
//				
//				System.out.println(acc.get(0).getId() + "******" + acc.get(0).getName());
////				accountListview.setItems(null);
//				accountListview.getItems().clear();
////				accountListview.setItems(acc);
////				accountListview.refresh();
//				accountListview.getItems().addAll(acc);
//				System.out.println(acc.size());
//				
//	            System.out.println("succeeded done");
//	            System.out.println(Thread.currentThread());
//
//	            super.succeeded();
//	        }
//
//	    }).start();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//���accountsListView�ؼ��Ľ���
				accountListview.getSelectionModel().clearSelection();
				
				System.out.println(acc.get(0).getId() + "******" + acc.get(0).getName());
//				accountListview.setItems(null);
				accountListview.getItems().clear();
//				accountListview.setItems(acc);
//				accountListview.refresh();
				accountListview.getItems().addAll(acc);
	            System.out.println("call done");
			}
		});
		
		
		//���ʱ���¼�
		accountListview.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends Account> ov, Account old_val,
						Account new_val) -> {
					if(new_val == null) {
						otherAccountLabel.setText("�Ҹ��������.....");
						return;
					}
					otherAccountName = new_val.getName();
//					otherAccountLabel.setText("���ں�\"" + otherAccountName + "\"����");
					TalkClient.theOtherAccount = new_val;
					System.out.println("chat with " + new_val.getId() + new_val.getName());
					while(!(new communication().sendChatWithOtherAccountId(new_val, TalkClient.socket))) {
						continue;
					}
				}
		);
	}
	
	public void flushAccountsListView(List<Account> accounts) {
		accountsList = accounts;
		
		for(Account account : accountsList) {
			System.out.println(account.getId() + "+++++" + account.getName());
		}
		
		int countAccounts = accountsList.size() - 1;
		
		//ɾ����������ʾ
//		for(Account account : accountsList) {
//			if(account.equals(TalkClient.account)) {
//				accountsList.remove(account);
//				break;
//			}
//		}
		if(accountListview == null) {
			System.out.println("error nulllllllll");
			return;
		}
		for(Account account : accountsList) {
			System.out.println(account.getId() + "...." + account.getName());
		}
		acc = FXCollections.observableArrayList(accountsList);
		
		
		

//		accountListview.getSelectionModel().clearSelection();
//		accountListview.setItems(FXCollections.observableList(accountsList));

//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				accountListview.getSelectionModel().clearSelection();
//				ObservableList<Account> acc = FXCollections.observableArrayList(accountsList);
//				accountListview.setItems(null);
//				accountListview.setItems(acc);
//				System.out.println("account listview reflush");			}
//		});
		
		
//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("run() has started");
//				accountListview.getSelectionModel().clearSelection();
//				ObservableList<Account> acc = FXCollections.observableArrayList(accountsList);
//				accountListview.setItems(acc);
//				accountListview.setCellFactory((ListView<Account> L) -> new accountCell());
				
//				accountListview.setCellFactory(new Callback<JFXListView<Account>, JFXListView<Account>>() {
//					@Override
//					public JFXListCell<Account> call(JFXListCell<Account> param) {
//						return new 
//					}
//				});
//				accountListview.setCellFactory(new Callback<ListView<Account>, ListCell<Account>>() {
//		            @Override
//		            public JFXListCell<Account> call(ListView<Account> param) {
//		                return new JFXListCell<Account>(){
//		                    @Override
//		                    public void updateItem(Account item, boolean empty) {
//		                        super.updateItem(item, empty);
//		                        setGraphic(null);
//		                        if(item!=null && !empty){
//		                        	HBox hBox = new HBox();
//		                        	hBox.getChildren().add(new Text(item.toString()));
//		                        	setGraphic(hBox);
//		                        	
//		                        	
////		                            HBox container = new HBox();
////		                            container.setMouseTransparent(true);
////		                            container.getChildren().add(new Label(item.getName()));
////		                            container.getChildren().add(new Label(item.getId()));
////		                            setGraphic(container);
//		                        }
//		                    }
//		                };
//		            }
//		        });
//				
//				
////				accountListview.setCellFactory(new Callback<ListView<Account>, ListCell<Account>>() {
////					
////					@Override
////					public JFXListCell<Account> call(ListView<Account> arg0) {
////						// TODO Auto-generated method stub
////						
////						final JFXListCell<Account> cell = new JFXListCell<Account>() {
////							@Override
////							public void updateItem(Account item, boolean empty) {
////								super.updateItem(item, empty);
////								setGraphic(null);
////								if(item != null && !empty) {
////									setText(item.toString());
////									sendTextField.setText("account");
////									System.out.println("account");
////									System.exit(-1);
////								}
////								else {
////									setText(null);
////								}
////							}
////						};
////						return cell;
////						
////						
//////						return null;
////						
//////						return new JFXListCell<Account>() {
//////							@Override
//////							public void updateItem(Account item, boolean empty) {
//////								super.updateItem(item, empty);
//////								setGraphic(null);
//////								setText(null);
//////								System.out.println("suprise !!!!!!!!!!");
//////								if(!empty && item != null) {
//////									HBox hBox = new HBox();
//////									Text name = new Text(item.getId() + "-" + item.getName());
//////									name.setFont(Font.font(16));
//////									hBox.getChildren().add(name);
//////									setGraphic(hBox);
//////								}
//////							}
//////						};
////						
////						
////					}
////				});
//				
//				
//				
//				System.out.println("setCellFactory has run");
////				accountListview.setCellFactory(e -> new ListCell<Account>() {
////					@Override
////					public void updateItem(Account item, boolean empty) {
////						
////						System.out.println("updateItem,,,,,,,,,,,,,,");
////						if(!empty && item != null) {
////							BorderPane cell = new BorderPane();
////							Text nameText = new Text(item.getId() + "-" + item.getName());
////							nameText.setFont(Font.font(16));
////							cell.setLeft(nameText);
////							
////							setGraphic(cell);
////							
////						}
////						super.updateItem(item, empty);
////						System.out.println("run.....................................");
////						sendTextField.setText("account");
////					}
////						
////				}
////				);
//			}
//		});
//		
//		//��accountListView����û������б�
//		ObservableList<Account> acc = FXCollections.observableArrayList(accountsList);
//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				//���accountsListView�ؼ��Ľ���
//				accountListview.getSelectionModel().clearSelection();
//				
//				System.out.println(acc.get(0).getId() + "******" + acc.get(0).getName());
////				accountListview.setItems(null);
//				accountListview.getItems().clear();
////				accountListview.setItems(acc);
////				accountListview.refresh();
//				accountListview.getItems().addAll(acc);
//			}
//		});
		
		
		
//		Platform.runLater(() -> {
//			//���accountsListView�ؼ��Ľ���
//			accountListview.getSelectionModel().clearSelection();
//			
//			//��accountListView����û������б�
//			ObservableList<Account> acc = FXCollections.observableArrayList(accountsList);
//			System.out.println(acc.get(0).getId() + "******" + acc.get(0).getName());
//			accountListview.setItems(null);
//			accountListview.setItems(acc);
////			accountListview.setItems(FXCollections.observableList(accountsList));
//			
////			ObservableList<Account> acc = FXCollections.observableList(accountsList);
////			accountListview = new JFXListView<Account>();
////			accountListview.setItems(acc);
//			
////			accountListview.setCellFactory(new Callback<ListView<Account>, ListCell<Account>>() {
////            @Override
////            public JFXListCell<Account> call(ListView<Account> param) {
////                return new JFXListCell<Account>(){
////                    @Override
////                    public void updateItem(Account item, boolean empty) {
////                        super.updateItem(item, empty);
////                        setGraphic(null);
////                        if(item != null && !empty){
//////                        	HBox hBox = new HBox();
//////                        	hBox.getChildren().add(new Text(item.toString()));
//////                        	setGraphic(hBox);
////                        	
////                        	
////                            HBox container = new HBox();
////                            container.setMouseTransparent(true);
////                            container.getChildren().add(new Label(item.getName()));
////                            container.getChildren().add(new Label(item.getId()));
////                            setGraphic(container);
////                        }
////                    }
////                };
////            }
////        });
//			
//			
//			//�Զ���accountListView
////			accountListview.setCellFactory((ListView<Account> L) -> new accountCell());
////			accountListview.setCellFactory(e -> new ListCell<Account>() {
////				@Override
////				public void updateItem(Account item, boolean empty) {
////					
////					System.out.println("updateItem,,,,,,,,,,,,,,");
////					if(!empty && item != null) {
////						BorderPane cell = new BorderPane();
////						Text nameText = new Text(item.getId() + "-" + item.getName());
////						nameText.setFont(Font.font(16));
////						cell.setLeft(nameText);
////						
////						setGraphic(cell);
////						
////					}
////					super.updateItem(item, empty);
////				}
////			}
////					
////					
////			);
//			
////			System.out.println("set done");
////			
//		});
//		
		
//		//���ʱ���¼�
//		accountListview.getSelectionModel().selectedItemProperty().addListener(
//				(ObservableValue<? extends Account> ov, Account old_val,
//						Account new_val) -> {
//					if(new_val == null) {
//						otherAccountLabel.setText("�Ҹ��������.....");
//						return;
//					}
//					otherAccountName = new_val.getName();
//					otherAccountLabel.setText("���ں�\"" + otherAccountName + "\"����");
//					TalkClient.theOtherAccount = new_val;
//					System.out.println("chat with" + new_val.getId() + new_val.getName());
//				}
//		);
		

		System.out.println(accountListview.toString());
		System.out.println("flush done!!!!!!");
		
	}

	static class accountCell extends JFXListCell<Account> {
		@Override
		public void updateItem(Account item, boolean empty) {
			System.out.println("accountCell started");
			super.updateItem(item, empty);
			setGraphic(null);
			setText(null);
			System.out.println(item.getId() + "this is accountCell");
			
			if(!empty && item != null) {
				BorderPane cell = new BorderPane();
				Text nameText = new Text(item.getId() + "-" + item.getName());
				nameText.setFont(Font.font(16));
				cell.setLeft(nameText);
				
				setGraphic(cell);
			}
			
			
//			if(!empty && item != null) {
//				
//				HBox hBox = new HBox();
//				hBox.setSpacing(5);
//				
//				//����ÿһ���û�����ʾ�����ʽ�����塢��С��
//				Text nameText = new Text(item.getId());
//				nameText.setFont(new Font("Verdana", 40));
//				System.out.println(item.getId() + "listview.......");
//				hBox.getChildren().addAll(nameText);
////				hBox.setStyle("-fx-background-color: blue;"); //����ɫ
//				hBox.setAlignment(Pos.CENTER_LEFT);
//				setGraphic(hBox);
//				
//			}
		}
	}
	

}
