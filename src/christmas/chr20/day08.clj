(ns christmas.chr20.day08
  (:require [clojure.string :as str]
            [christmas.tools :as t]))

(defn parse-line [line]
  (->> (re-seq #"acc|jmp|nop|-?\d+" line)
       (#(vector (keyword (first %)) (t/parse-int (last %))))))

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map parse-line)
       (zipmap (range))))

(defn acc [game]
  (-> (update game :acc (partial + (last ((:instr game) (:pos game)))))
      (update :instr #(dissoc % (:pos game)))
      (update :pos inc)))

(defn jmp [game]
  (-> (update game :instr #(dissoc % (:pos game)))
      (update :pos (partial + (last ((:instr game) (:pos game)))))))

(defn nop [game]
  (-> (update game :instr #(dissoc % (:pos game)))
      (update :pos inc)))

(defn stp [game]
  (-> (update game :instr #(dissoc % (:pos game)))
      (update :pos (constantly -1))))

(def funcs {:nop nop :jmp jmp :acc acc})

(defn tick [game]
  (-> (first ((:instr game) (:pos game)))
      (#((funcs % stp) game))))

(defn day08 [input]
  (->> (parsefile input)
       (hash-map :pos 0 :acc 0 :instr)
       (iterate tick)
       (drop-while #(not= (:pos %) -1))
       (first)
       (:acc)))

(defn flip [mappings number]
  (update-in mappings [number 0] {:nop :jmp :jmp :nop :acc :acc}))

(defn create-chaos [mappings]
  (->> (range (count mappings))
       (map (partial flip mappings))
       (remove (partial = mappings))))

(defn thingy [instruction]
  (->> (hash-map :pos 0 :acc 0 :len (count instruction) :instr instruction)
       (iterate tick)
       (drop-while #(not (#{-1 (:len %)} (:pos %))))
       (first)
       (#(vector (:acc %) (:pos %)))))

(defn day08b [input]
  (->> (parsefile input)
       (create-chaos)
       (map thingy)
       (filter (fn [[_ b]] (not= -1 b)))
       (ffirst)))

