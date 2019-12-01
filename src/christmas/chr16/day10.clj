(ns christmas.chr16.day10
  	(:require [christmas.tools :as tools])
   (:require [clojure.string :as str]))

(defn convert[[type number]]
    [(keyword type)
    (tools/parse-int number)])

(defn parse-instructions[instructions]
    (->> (partition 2 instructions)
         (map convert)))

(defn parse-line [line]
    (->> (re-seq #"\d+|bot|output|value" line)
         parse-instructions))

(defn parse-bot [bot]
    (->> (parse-line bot)
         (#(vector (second (first %)) {:low (second %) :high (last %)}))))

(defn parse-value [value]
    (->> (parse-line value)
         (map second)))

(defn parse-file [filename]
    (->> (str/split-lines filename)
         sort
         (split-with #(str/starts-with? % "bot"))
         (#(hash-map :bots (apply hash-map (flatten (map parse-bot (first %))))
                     :values (apply hash-map (flatten (map parse-value (last %))))
                     :outputs {}))))

(defn bot-has-value[bots [value bot]]
    (->> (bots bot)
         :value
         some?))

(defn move-value[{:keys [bots values outputs]}  [value bot]]
    (let [wanted-bot (bots bot)]
        {:bots (assoc bots bot (assoc wanted-bot :value value))
         :values (dissoc values value)
         :outputs outputs
        }))

(defn add-value-to-output[game output value]
    (update-in game [:outputs] #(assoc % output value)))

(defn add-value-to-queue[game bot value]
    (update-in game [:values] #(assoc % value bot)))
     
                    
(defn do-things[game [type number] value]
    (->> (if (= :bot type)
            (add-value-to-queue game number value)
            (add-value-to-output game number value)
    )))

                    
(defn update-bots-n-outputs[game {:keys [low high]} [low-value high-value]]
    (-> (do-things game low low-value)
        (do-things high high-value)
    ))

        
(defn calculate[{:keys [bots values outputs] :as game} [value bot]]
    (let [wanted-bot (bots bot)
          first-value (:value wanted-bot)]
        (if (= #{value first-value} #{61 17})
            (println "Bot " bot " compares 61 and 17"))
        (-> (update-in game [:bots] #(dissoc % bot))
            (update-in [:values] #(dissoc % value))
            (update-bots-n-outputs (dissoc wanted-bot :value) (sort [value first-value]) 
     ))))
        
(defn tick[game]
    (let [next-value (first (:values game))
          bots (:bots game)]
        (->> (if (bot-has-value bots next-value)
                 (calculate game next-value)
                 (move-value game next-value)))
    ))

(defn play[file]
    (->> (parse-file file)
         (iterate tick)
         (drop-while #(< 0 (count (:bots %))))
         first
         :outputs
         (#(* (% 0) (% 1) (% 2)))
        ))