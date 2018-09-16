(ns datapudding-server.comment-test
  (:require [clojure.test :refer :all]
            [io.pedestal.test :refer :all]
            [io.pedestal.http :as bootstrap]
            [datapudding-server.service-test :refer [service]]))

(deftest get-comment-test
  (is (=
       (:body (response-for service :get "/comment"))
       "This is a comment"))
  (is (=
       (:headers (response-for service :get "/comment"))
       {"Content-Type" "text/html;charset=UTF-8"
        "Strict-Transport-Security" "max-age=31536000; includeSubdomains"
        "X-Frame-Options" "DENY"
        "X-Content-Type-Options" "nosniff"
        "X-XSS-Protection" "1; mode=block"
        "X-Download-Options" "noopen"
        "X-Permitted-Cross-Domain-Policies" "none"
        "Content-Security-Policy" "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;"})))
