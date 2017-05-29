//
//  ViewController.swift
//  WebViewDemo
//
//  Created by Woocheol Park on 2017. 5. 29..
//  Copyright © 2017년 Woocheol Park. All rights reserved.
//
import Foundation
import UIKit
import WebKit


class ViewController: UIViewController, WKScriptMessageHandler {
    
    var webView: WKWebView!
    
    let webViewConfig = WKWebViewConfiguration()
    
    override func loadView() {
        super.loadView()
        
        let contentController = WKUserContentController()
        
        contentController.add(self, name: "handler")
        contentController.add(self, name: "message")
        webViewConfig.userContentController = contentController
        webView = WKWebView(frame: .zero, configuration: webViewConfig)
        self.view = webView
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let url = "url"
        
        let serviceUrl = URL(string: ("\(url)").addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!)
        
        
        let request = URLRequest(url: serviceUrl!)
        
        webView.load(request)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if(message.name == "handler"){
            let responseHandleMessage = String(describing: message.body)
            if(responseHandleMessage == "complete"){
                // Complete Action
            }
        }else if(message.name == "message"){
            let responseMessage = String(describing: message.body)
            displayAlertMessage("알림", message: responseMessage)
        }
    }
    
    func displayAlertMessage(_ title:String,message:String){
        let alert = UIAlertController(title:title,message:message,preferredStyle: UIAlertControllerStyle.alert)
        let action = UIAlertAction(title: "확인", style: UIAlertActionStyle.default, handler: nil)
        
        alert.addAction(action)
        self.present(alert, animated: true, completion: nil)
    }
    
}

