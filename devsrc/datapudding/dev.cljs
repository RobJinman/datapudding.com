(ns datapudding.dev
  (:require [datapudding.core :as datapudding]
            [figwheel.client :as fw]))

(fw/start {:on-jsload datapudding/run
           :websocket-url "ws://localhost:3449/figwheel-ws"})
