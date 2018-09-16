(ns datapudding-server.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [datapudding-server.article :as article]
            [datapudding-server.comment :as comment]))

(def common-interceptors [(body-params/body-params) http/html-body])

(def routes #{["/comment" :get (conj common-interceptors `comment/get-comment)]
              ["/article" :get (conj common-interceptors `article/get-article)]})

(def service-map {:env :prod
                  ;; ::http/interceptors []
                  ::http/routes routes
                  
                  ::http/allowed-origins ["http://datapudding.com:80"
                                          "http://localhost:8890"]
                  
                  ;; Tune the Secure Headers
                  ;; and specifically the Content Security Policy appropriate to your service/application
                  ;; For more information, see: https://content-security-policy.com/
                  ;;   See also: https://github.com/pedestal/pedestal/issues/499
                  ;;::http/secure-headers {:content-security-policy-settings {:object-src "'none'"
                  ;;                                                          :script-src "'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:"
                  ;;                                                          :frame-ancestors "'none'"}}
                  
                  ::http/resource-path "/public"
                  
                  ::http/type :jetty
                  ;;::http/host "localhost"
                  ::http/port 8890
                  ;; Options to pass to the container (Jetty)
                  ::http/container-options {:h2c? true
                                            :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                            :ssl? false}})
