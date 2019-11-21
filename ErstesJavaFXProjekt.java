btn3.setOnAction(new EventHandler<ActionEvent>() { // ActionHandler ab sofort mit btn1.setOnAction(e->{...});
            @Override
            public void handle(final ActionEvent e) {
                File file = directoryChooser.showDialog(primaryStage);
                Image image = new Image(file.toURI().toString());
                if (file != null) {
                    try {
                    	ImageView imageView = new ImageView(image);
                        ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),
                                null), "png",file);
                        
                        thirdStage.add(image,zeile,spalte);
                        zeile++;
                        if(zeile > 5) {
                        	  spalte++;
                        	  zeile = 0;
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(
                            Spielerei.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });