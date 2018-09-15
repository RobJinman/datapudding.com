(ns datapudding-server.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [datapudding-server.service :as service]))

(defonce server (atom nil))

(def service-map-dev
  (-> service/service-map ;; start with production configuration
      (merge {:env :dev
              ;; do not block thread that starts web server
              ::http/join? false
              ;; Routes can be a function that resolve routes,
              ;;  we can use this to set the routes to be reloadable
              ::http/routes #(route/expand-routes (deref #'service/routes))
              ;; all origins are allowed in dev mode
              ::http/allowed-origins {:creds true :allowed-origins (constantly true)}
              ;; Content Security Policy (CSP) is mostly turned off in dev mode
              ::http/secure-headers {:content-security-policy-settings {:object-src "'none'"}}})
      ;; Wire up interceptor chains
      http/default-interceptors
      http/dev-interceptors))

(defn start-dev
  "The entry-point for 'lein run-dev'"
  []
  (println "\nCreating your [DEV] server...")
  (reset! server (-> service-map-dev
                     http/create-server
                     http/start)))

(defn stop-dev
  []
  (http/stop @server))

(defn restart-dev
  []
  (stop-dev)
  (start-dev))

(defn print-routes
  "Print our application's routes"
  []
  (route/print-routes (table-routes service/routes)))

(defn named-route
  "Finds a route by name"
  [route-name]
  (->> service/routes
       table-routes
       (filter #(= route-name (:route-name %)))
       first))

(defn -main
  "The entry-point for 'lein run'"
  []
  (println "\nCreating your server...")
  (http/start (http/create-server service/service-map)))

