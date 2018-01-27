(ns datapudding.macros)


(defn criticise-code
  [msg code]
  `(println ~msg (quote ~code)))

(defmacro code-critic
  [bad good]
  `(do ~@(map #(apply criticise-code %)
              [["Great squid of Madrid, this is bad code:" bad]
               ["Sweet gorilla of Manila, this is good code:" good]])))
